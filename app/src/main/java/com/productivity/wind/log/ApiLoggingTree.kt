package com.productivity.wind.log

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber
import java.io.IOException
import java.util.concurrent.ConcurrentLinkedQueue


class ApiLoggingTree(
    private val isEnabled: Boolean,
    private val authToken: String,
    /**
     * Airtable API endpoint URL.
     *
     * Example: https://api.airtable.com/v0/appXXXXXXXXX/Table%20Name
     *
     * Subject to rate limit:
     * https://airtable.com/developers/web/api/rate-limits
     */
    private val endpointUrl: String,
) : Timber.Tree() {
    private val client = OkHttpClient()
    private val logQueue = ConcurrentLinkedQueue<LogMessage>()
    private var flushJob: Job? = null
    private var logSequence = 1
    private var apiFailureCount = 0

    companion object {

        private const val MAX_LOG_COUNT_PER_SECOND = 5

        private const val MAX_RECORDS_PER_REQUEST = 10

        private const val MAX_SUBSEQUENT_API_FAILURE_COUNT = 10
    }

    init {
        if (isEnabled) {
            startFlushJob()
        }
    }

    override fun log(
        priority: Int,
        tag: String?,
        message: String,
        t: Throwable?,
    ) {
        if (!isEnabled) {
            return
        }

        logQueue.add(LogMessage(priority, tag, message, t, logSequence++))

        if ((flushJob == null || flushJob?.isCancelled == true) && !apiFailureCount.exceededFailureCount()) {
            startFlushJob()
        }
    }

    private fun startFlushJob() {
        flushJob =
            CoroutineScope(Dispatchers.IO).launch {
                while (isActive) {
                    if (apiFailureCount.exceededFailureCount()) {
                        Timber.e("Flushing job stopped due to repeated failures.")
                        break
                    }

                    flushLogs()

                    // https://airtable.com/developers/web/api/rate-limits
                    delay(1_100L)
                }
            }
    }

    private fun createLogMessage(logs: List<LogMessage>): String? {
        if (logs.isEmpty()) {
            return null
        }

        val records = JSONArray().apply { logs.forEach { put(it.toLogRecord()) } }
        return JSONObject().apply {
            put("records", records)
        }.toString()
    }

    private fun getMaximumAllowedLogs(): List<LogMessage> {
        val logs = mutableListOf<LogMessage>()
        while (logQueue.isNotEmpty() && logs.size < MAX_RECORDS_PER_REQUEST) {
            val log = logQueue.poll()
            if (log != null) {
                logs.add(log)
            }
        }
        return logs
    }

    private suspend fun flushLogs() {
        var sentLogCount = 0

        while (sentLogCount < MAX_LOG_COUNT_PER_SECOND) {
            val jsonPayload = createLogMessage(getMaximumAllowedLogs())
            if (jsonPayload != null) {
                sendLogToApi(jsonPayload)
                sentLogCount++

                delay(100L)
            }
        }

        if (logQueue.isEmpty()) {
            flushJob?.cancel()
        }
    }

    private fun sendLogToApi(logPayloadJson: String) {
        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val body = logPayloadJson.toRequestBody(mediaType)
        val request =
            Request.Builder()
                .url(endpointUrl)
                .addHeader("Authorization", "Bearer $authToken")
                .post(body)
                .build()

        client.newCall(request).enqueue(
            object : okhttp3.Callback {
                override fun onFailure(
                    call: okhttp3.Call,
                    e: IOException,
                ) {
                    Timber.e("Failed to send log to API: ${e.localizedMessage}")
                    apiFailureCount++
                }

                override fun onResponse(
                    call: okhttp3.Call,
                    response: okhttp3.Response,
                ) {
                    response.use { // This ensures the response body is closed
                        if (!response.isSuccessful) {
                            Timber.e(
                                "Log is rejected: HTTP code: ${response.code}, " +
                                    "message: ${response.message}, body: ${response.body?.string()}",
                            )
                            apiFailureCount++
                        } else {
                            // Reset failure count on successful response.
                            apiFailureCount = 0
                        }
                    }
                }
            },
        )
    }

    private fun Int.exceededFailureCount(): Boolean {
        return this >= MAX_SUBSEQUENT_API_FAILURE_COUNT
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ApiLoggingTree

        return endpointUrl == other.endpointUrl
    }

    override fun hashCode(): Int {
        return endpointUrl.hashCode()
    }
}
