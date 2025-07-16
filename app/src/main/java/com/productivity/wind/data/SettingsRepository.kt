package com.productivity.wind.data
import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.productivity.wind.util.AppConfig.DEFAULT_APP_CHECK_INTERVAL_MIN
import com.productivity.wind.util.AppConfig.MINIMUM_APP_CHECK_INTERVAL_MIN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

/** Extension property to provide a [DataStore] instance for application settings, named "app_settings". */
private val Context.dataStore by preferencesDataStore(name = "app_settings")


class SettingsRepository(private val context: Context) {
    companion object {
        /** [Preferences.Key] for storing the app check interval in minutes. */
        val APP_CHECK_INTERVAL = intPreferencesKey("app_check_interval")

        /** [Preferences.Key] for storing the toggle state of forcing app starts. */
        val ENABLE_FORCE_START_APPS = booleanPreferencesKey("enable_force_start_apps")

        /** [Preferences.Key] for storing the toggle state of health checks. */
        val ENABLE_HEALTH_CHECK = booleanPreferencesKey("enable_health_check")

        /** [Preferences.Key] for storing the UUID used for health checks. */
        val HEALTH_CHECK_UUID = stringPreferencesKey("health_check_uuid")

        /** [Preferences.Key] for storing the toggle state of remote logging. */
        val ENABLE_REMOTE_LOGGING = booleanPreferencesKey("enable_remote_logging")

        /** [Preferences.Key] for storing the Airtable API token. */
        val AIRTABLE_TOKEN = stringPreferencesKey("airtable_token")

        /** [Preferences.Key] for storing the Airtable data URL. */
        val AIRTABLE_DATA_URL = stringPreferencesKey("airtable_data_url")
    }

    val appCheckIntervalFlow: Flow<Int> =
        context.dataStore.data
            .map { preferences ->
                val interval = preferences[APP_CHECK_INTERVAL] ?: DEFAULT_APP_CHECK_INTERVAL_MIN
                if (interval < MINIMUM_APP_CHECK_INTERVAL_MIN) MINIMUM_APP_CHECK_INTERVAL_MIN else interval
            }


    val enableForceStartAppsFlow: Flow<Boolean> =
        context.dataStore.data
            .map { preferences ->
                preferences[ENABLE_FORCE_START_APPS] ?: false // Default to disabled
            }

    val enableHealthCheckFlow: Flow<Boolean> =
        context.dataStore.data
            .map { preferences ->
                preferences[ENABLE_HEALTH_CHECK] ?: false // Default to disabled
            }

    val healthCheckUUIDFlow: Flow<String> =
        context.dataStore.data
            .map { preferences ->
                preferences[HEALTH_CHECK_UUID] ?: "" // Default to empty string
            }

    val enableRemoteLoggingFlow: Flow<Boolean> =
        context.dataStore.data
            .map { preferences ->
                preferences[ENABLE_REMOTE_LOGGING] ?: false // Default to disabled
            }


    val airtableTokenFlow: Flow<String> =
        context.dataStore.data
            .map { preferences ->
                preferences[AIRTABLE_TOKEN] ?: "" // Default to empty string
            }

    val airtableDataUrlFlow: Flow<String> =
        context.dataStore.data
            .map { preferences ->
                preferences[AIRTABLE_DATA_URL] ?: "" // Default to empty string
            }

    /**
     * A [Flow] that emits an [AirtableConfig] object, combining the states of
     * remote logging enabled, Airtable token, and Airtable data URL.
     * This is useful for consumers that need all Airtable-related settings together.
     */
    val airtableConfig: Flow<AirtableConfig> =
        combine(
            enableRemoteLoggingFlow,
            airtableTokenFlow,
            airtableDataUrlFlow,
        ) { isRemoteLoggingEnabled, airtableToken, airtableDataUrl ->
            AirtableConfig(isRemoteLoggingEnabled, airtableToken, airtableDataUrl)
        }

    /**
     * Saves the app check interval in minutes to DataStore.
     * Note: The actual interval used by the app will be at least [MINIMUM_APP_CHECK_INTERVAL_MIN],
     * as enforced by [appCheckIntervalFlow].
     *
     * @param interval The desired interval in minutes.
     */
    suspend fun saveAppCheckInterval(interval: Int) {
        context.dataStore.edit { preferences ->
            preferences[APP_CHECK_INTERVAL] = interval
        }
    }

    /**
     * Saves the preference for enabling or disabling the force starting of apps.
     * When enabled, apps will be started even if they were recently running.
     *
     * @param enabled `true` to enable force starting apps, `false` to disable.
     */
    suspend fun saveEnableForceStartApps(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[ENABLE_FORCE_START_APPS] = enabled
        }
    }

    /**
     * Saves the preference for enabling or disabling health checks.
     * Health checks typically involve pinging an external URL.
     *
     * @param enabled `true` to enable health checks, `false` to disable.
     */
    suspend fun saveEnableHealthCheck(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[ENABLE_HEALTH_CHECK] = enabled
        }
    }

    /**
     * Saves the Health Check UUID to DataStore.
     * This UUID is typically part of the URL pinged for health checks.
     *
     * @param uuid The UUID string for health checks.
     */
    suspend fun saveHealthCheckUUID(uuid: String) {
        context.dataStore.edit { preferences ->
            preferences[HEALTH_CHECK_UUID] = uuid
        }
    }

    /**
     * Saves the preference for enabling or disabling remote logging (e.g., to Airtable).
     *
     * @param enabled `true` to enable remote logging, `false` to disable.
     */
    suspend fun saveEnableRemoteLogging(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[ENABLE_REMOTE_LOGGING] = enabled
        }
    }

    /**
     * Saves the Airtable authentication token to DataStore.
     *
     * @param token The Airtable API token.
     */
    suspend fun saveAirtableToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[AIRTABLE_TOKEN] = token
        }
    }

    /**
     * Saves the Airtable data URL to DataStore.
     * This is the endpoint URL where log data will be sent if remote logging is enabled.
     *
     * @param url The URL for the Airtable data endpoint.
     */
    suspend fun saveAirtableDataUrl(url: String) {
        context.dataStore.edit { preferences ->
            preferences[AIRTABLE_DATA_URL] = url
        }
    }
}
