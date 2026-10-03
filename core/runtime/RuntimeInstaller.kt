package com.coral.launcher.core.runtime

import android.content.Context
import java.io.File

class RuntimeInstaller(
    private val context: Context
) {

    fun getRuntimeDirectory(version: String): File {
        return File(
            context.getExternalFilesDir(null),
            "runtimes/java-$version"
        )
    }

    fun isInstalled(version: String): Boolean {
        val runtimeDir = getRuntimeDirectory(version)
        val javaBinary = File(runtimeDir, "bin/java")

        return runtimeDir.isDirectory && javaBinary.isFile
    }

    fun prepareRuntimeDirectory(version: String): File {
        val directory = getRuntimeDirectory(version)

        if (!directory.exists()) {
            directory.mkdirs()
        }

        return directory
    }
}
