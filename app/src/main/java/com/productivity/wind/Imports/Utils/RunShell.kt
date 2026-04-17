import java.io.*
import java.util.concurrent.CopyOnWriteArrayList

object LinuxCore {

    private var process: Process? = null
    private var writer: BufferedWriter? = null
    private var reader: BufferedReader? = null

    var rootfsPath: String = ""
    var prootPath: String = ""

    // simple command history (your “Termux feature”)
    val history = CopyOnWriteArrayList<String>()

    fun start() {
        if (process != null) return

        if (rootfsPath.isEmpty() || prootPath.isEmpty()) {
            throw IllegalStateException("Missing rootfs or proot path")
        }

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

    fun run(command: String): String {
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

    // FIRST BOOT (this is what makes pip possible)
    fun bootstrapPython(): String {
        return run("""
            apt update
            apt install -y python3 python3-pip
        """.trimIndent())
    }

    // pip works ONLY after bootstrap
    fun pipInstall(pkg: String): String {
        return run("pip3 install $pkg --break-system-packages")
    }

    fun installPackage(pkg: String): String {
        return run("apt install -y $pkg")
    }

    fun stop() {
        process?.destroy()
        process = null
        writer = null
        reader = null
    }
}
