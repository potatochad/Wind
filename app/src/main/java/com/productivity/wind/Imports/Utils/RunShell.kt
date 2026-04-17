import java.io.*

object LinuxTerminal {

    private var process: Process? = null
    private var writer: BufferedWriter? = null
    private var reader: BufferedReader? = null

    fun start(shell: String = "sh") {
        if (process != null) return

        process = ProcessBuilder(shell)
            .redirectErrorStream(true)
            .start()

        writer = BufferedWriter(OutputStreamWriter(process!!.outputStream))
        reader = BufferedReader(InputStreamReader(process!!.inputStream))
    }

    fun run(command: String): String {
        start()

        writer?.apply {
            write(command)
            newLine()
            flush()
        }

        val output = StringBuilder()
        while (reader!!.ready()) {
            output.append(reader!!.readLine()).append("\n")
        }

        return output.toString().trim()
    }

    fun stop() {
        try {
            writer?.close()
            reader?.close()
            process?.destroy()
        } finally {
            writer = null
            reader = null
            process = null
        }
    }
}



