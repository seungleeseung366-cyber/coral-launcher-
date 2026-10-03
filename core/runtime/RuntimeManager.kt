package com.coral.launcher.core.runtime

import android.content.Context

class RuntimeManager(
    context: Context
) {

    private val detector = RuntimeDetector(context)
    private val installer = RuntimeInstaller(context)

    fun getInstalledRuntimes(): List<JavaRuntime> {
        return detector.detect()
    }

    fun isRuntimeInstalled(version: String): Boolean {
        return installer.isInstalled(version)
    }

    fun prepareRuntime(version: String) =
        installer.prepareRuntimeDirectory(version)

    fun findRuntime(version: String): JavaRuntime? {
        return getInstalledRuntimes()
            .firstOrNull { runtime ->
                runtime.version.startsWith(version)
            }
    }
}
