package com.coral.launcher.core.game

import java.io.File

class VersionManager(
    private val gameDirectory: File
) {

    fun getInstalledVersions(): List<String> {
        val versionsDirectory = File(gameDirectory, "versions")

        if (!versionsDirectory.exists()) {
            return emptyList()
        }

        return versionsDirectory
            .listFiles()
            ?.filter { it.isDirectory }
            ?.map { it.name }
            ?.sorted()
            ?: emptyList()
    }

    fun isInstalled(versionId: String): Boolean {
        val versionDirectory = File(
            gameDirectory,
            "versions/$versionId"
        )

        val versionJson = File(
            versionDirectory,
            "$versionId.json"
        )

        return versionDirectory.isDirectory &&
                versionJson.isFile
    }

    fun getVersionDirectory(versionId: String): File {
        return File(
            gameDirectory,
            "versions/$versionId"
        )
    }

    fun getVersionJson(versionId: String): File {
        return File(
            getVersionDirectory(versionId),
            "$versionId.json"
        )
    }

    fun createVersionDirectory(versionId: String): File {
        val directory = getVersionDirectory(versionId)

        if (!directory.exists()) {
            directory.mkdirs()
        }

        return directory
    }
}
