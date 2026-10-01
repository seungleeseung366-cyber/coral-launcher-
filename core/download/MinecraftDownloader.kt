package com.coral.launcher.download

import com.coral.launcher.game.GamePaths
import com.coral.launcher.game.MinecraftVersion
import android.content.Context
import org.json.JSONObject
import java.io.File

class MinecraftDownloader(
    private val context: Context
) {

    fun downloadVersion(
        version: MinecraftVersion
    ): DownloadResult {

        return try {
            val versionDir =
                GamePaths.version(context, version.id)

            versionDir.mkdirs()

            val jsonFile = File(
                versionDir,
                "${version.id}.json"
            )

            val result = FileDownloader.download(
                version.url,
                jsonFile
            )

            if (result is DownloadResult.Error) {
                return result
            }

            DownloadResult.Success(jsonFile)

        } catch (e: Exception) {

            DownloadResult.Error(
                message = e.message
                    ?: "Failed to download Minecraft version",
                cause = e
            )
        }
    }

    fun getClientUrl(
        versionJson: File
    ): String? {

        return try {
            val json = JSONObject(
                versionJson.readText()
            )

            json
                .getJSONObject("downloads")
                .getJSONObject("client")
                .getString("url")

        } catch (_: Exception) {
            null
        }
    }

    fun downloadClient(
        version: MinecraftVersion,
        versionJson: File
    ): DownloadResult {

        val url = getClientUrl(versionJson)
            ?: return DownloadResult.Error(
                "Client download URL not found"
            )

        val destination = File(
            GamePaths.version(
                context,
                version.id
            ),
            "${version.id}.jar"
        )

        return FileDownloader.download(
            url,
            destination
        )
    }
}
