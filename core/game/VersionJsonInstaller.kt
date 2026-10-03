package com.coral.launcher.core.game

import com.coral.launcher.core.download.VersionJsonDownloader
import org.json.JSONObject
import java.io.File

class VersionJsonInstaller(
    private val gameDirectory: File
) {

    private val downloader = VersionJsonDownloader()

    fun install(
        version: MinecraftVersion
    ): JSONObject? {
        return try {
            val versionDirectory = File(
                gameDirectory,
                "versions/${version.id}"
            )

            versionDirectory.mkdirs()

            val destination = File(
                versionDirectory,
                "${version.id}.json"
            )

            if (!downloader.download(
                    url = version.url,
                    destination = destination
                )
            ) {
                return null
            }

            JSONObject(destination.readText())
        } catch (_: Exception) {
            null
        }
    }
}
