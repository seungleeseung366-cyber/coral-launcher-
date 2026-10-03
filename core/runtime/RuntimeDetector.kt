package com.coral.launcher.core.runtime

import android.content.Context
import java.io.File

class RuntimeDetector(
    private val context: Context
) {

    fun detect(): List<JavaRuntime> {
        val runtimes = mutableListOf<JavaRuntime>()

        val candidates = listOf(
            File(context.filesDir, "runtimes/java-8"),
            File(context.filesDir, "runtimes/java-17"),
            File(context.filesDir, "runtimes/java-21"),
            File(context.getExternalFilesDir(null), "runtimes/java-8"),
            File(context.getExternalFilesDir(null), "runtimes/java-17"),
            File(context.getExternalFilesDir(null), "runtimes/java-21")
        )

        for (home in candidates) {
            val javaBinary = File(home, "bin/java")

            if (home.exists() && javaBinary.exists()) {
                val version = detectVersion(javaBinary)

                runtimes.add(
                    JavaRuntime(
                        javaHome = home,
                        javaBinary = javaBinary,
                        version = version
                    )
                )
            }
        }

        return runtimes.distinctBy { it.javaHome.absolutePath }
    }

    private fun detectVersion(javaBinary: File): String {
        return try {
            val process = ProcessBuilder(
                javaBinary.absolutePath,
                "-version"
            )
                .redirectErrorStream(true)
                .start()

            val output = process.inputStream
                .bufferedReader()
                .use { it.readText() }

            process.waitFor()

            Regex("""version "([^"]+)"""")
                .find(output)
                ?.groupValues
                ?.getOrNull(1)
                ?: "unknown"
        } catch (_: Exception) {
            "unknown"
        }
    }
                  }
