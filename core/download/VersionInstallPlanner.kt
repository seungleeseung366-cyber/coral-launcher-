package com.coral.launcher.core.game

import java.io.File

class VersionInstallPlanner(
    private val gameDirectory: File
) {

    fun createPlan(
        metadata: VersionMetadata
    ): VersionInstallPlan {
        val versionDirectory = File(
            gameDirectory,
            "versions/${metadata.id}"
        )

        val librariesDirectory = File(
            gameDirectory,
            "libraries"
        )

        val assetsDirectory = File(
            gameDirectory,
            "assets"
        )

        val assetIndexFile = File(
            assetsDirectory,
            "indexes/${metadata.assetIndexId ?: metadata.id}.json"
        )

        val clientJar = File(
            versionDirectory,
            "${metadata.id}.jar"
        )

        return VersionInstallPlan(
            versionId = metadata.id,
            versionDirectory = versionDirectory,
            clientJar = clientJar,
            librariesDirectory = librariesDirectory,
            assetsDirectory = assetsDirectory,
            assetIndexFile = assetIndexFile
        )
    }
}
