package com.coral.launcher.core.launcher

class GameProcess(
    private val process: Process
) {

    fun isRunning(): Boolean {
        return process.isAlive
    }

    fun waitFor(): Int {
        return process.waitFor()
    }

    fun destroy() {
        if (process.isAlive) {
            process.destroy()
        }
    }

    fun forceDestroy() {
        if (process.isAlive) {
            process.destroyForcibly()
        }
    }

    fun readOutput(onLine: (String) -> Unit) {
        Thread {
            process.inputStream
                .bufferedReader()
                .useLines { lines ->
                    lines.forEach(onLine)
                }
        }.start()
    }
}
