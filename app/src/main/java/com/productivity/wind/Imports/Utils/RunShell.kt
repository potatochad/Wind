package com.productivity.wind.Imports.Utils

import com.productivity.wind.Imports.Utils.String.*
import java.io.*

import java.util.concurrent.CopyOnWriteArrayList

object LinuxCore {

    private var process: java.lang.Process? = null
    private var writer: BufferedWriter? = null
    private var reader: BufferedReader? = null

    var rootfsPath: Str = ""
    var prootPath: Str = ""

    // simple command history (your “Termux feature”)
    val history = CopyOnWriteArrayList<String>()

    fun start() {
        if (process != null) return

        // Do {
        if (rootfsPath.empty || prootPath.empty) {
            throw IllegalStateException("Missing rootfs or proot path")
        }
        // }

        val cmd = arrayOf(
            "/system/bin/sh",
            "-c",
            """
            $prootPath -0 -r $rootfsPath \
            -b /dev -b /proc -b /sys -b /sdcard \
            /bin/bash --login
            """.trimIndent()
        )

        process = ProcessBuilder(*cmd)
            .redirectErrorStream(true)
            .start()

        writer = BufferedWriter(OutputStreamWriter(process!!.outputStream))
        reader = BufferedReader(InputStreamReader(process!!.inputStream))
    }

    fun run(command: Str): Str {
        start()

        history.add(command)

        writer?.apply {
            write(command)
            newLine()
            flush()
        }

        val output = StringBuilder()

        while (true) {
            val line = reader?.readLine() ?: break
            output.append(line).append("\n")

            if (!reader!!.ready()) break
        }

        return output.toString().trim()
    }

    fun installPackage(pkg: Str): Str {
        return run("apt install -y $pkg")
    }

    fun stop() {
        process?.destroy()
        process = null
        writer = null
        reader = null
    }
}






