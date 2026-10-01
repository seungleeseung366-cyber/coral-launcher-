package com.coral.launcher.download

import android.content.Context
import com.coral.launcher.game.GamePaths
import com.coral.launcher.game.MinecraftVersion
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
                GamePaths.version(
                    context,
                    version.id
                )

            versionDir.mkdirs()

            val jsonFile = File(
                versionDir,
                "${version.id}.json"
            )

            val result = FileDownloader.download(
                url = version.url,
                destination = jsonFile,
                expectedSha1 = version.sha1
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

            val json =
                JSONObject(versionJson.readText())

            json
                .getJSONObject("downloads")
                .getJSONObject("client")
                .getString("url")

        } catch (_: Exception) {
            null
        }
    }

    fun getClientSha1(
        versionJson: File
    ): String? {

        return try {

            val json =
                JSONObject(versionJson.readText())

            json
                .getJSONObject("downloads")
                .getJSONObject("client")
                .optString("sha1")
                .takeIf {
                    it.isNotBlank()
                }

        } catch (_: Exception) {
            null
        }
    }

    fun downloadClient(
        version: MinecraftVersion,
        versionJson: File
    ): DownloadResult {

        val url =
            getClientUrl(versionJson)
                ?: return DownloadResult.Error(
                    "Client download URL not found"
                )

        val sha1 =
            getClientSha1(versionJson)

        val destination = File(
            GamePaths.version(
                context,
                version.id
            ),
            "${version.id}.jar"
        )

        return FileDownloader.download(
            url = url,
            destination = destination,
            expectedSha1 = sha1
        )
    }
}
