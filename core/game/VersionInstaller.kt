package com.coral.launcher.core.game

import com.coral.launcher.core.download.MinecraftDownloader
import java.io.File

class VersionInstaller(
    private val gameDirectory: File
) {

    private val versionManager = VersionManager(gameDirectory)

    fun isInstalled(versionId: String): Boolean {
        return versionManager.isInstalled(versionId)
    }

    fun prepareVersion(versionId: String): File {
        return versionManager.createVersionDirectory(versionId)
    }

    fun getVersionJson(versionId: String): File {
        return versionManager.getVersionJson(versionId)
    }
}
