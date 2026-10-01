package com.coral.launcher.download

import android.content.Context
import com.coral.launcher.game.GamePaths
import org.json.JSONObject
import java.io.File

class LibraryDownloader(
    private val context: Context
) {

    fun downloadLibraries(
        versionId: String,
        versionJson: File
    ): List<File> {

        val result = mutableListOf<File>()

        val json = JSONObject(
            versionJson.readText()
        )

        val libraries =
            json.optJSONArray("libraries")
                ?: return result

        for (i in 0 until libraries.length()) {

            val library =
                libraries.getJSONObject(i)

            val downloads =
                library.optJSONObject("downloads")
                    ?: continue

            val artifact =
                downloads.optJSONObject("artifact")
                    ?: continue

            val url =
                artifact.optString("url")

            val path =
                artifact.optString("path")

            val expectedSha1 =
                artifact.optString("sha1")

            if (
                url.isBlank() ||
                path.isBlank()
            ) {
                continue
            }

            val destination = File(
                GamePaths.libraries(context),
                path
            )

            when (
                FileDownloader.download(
                    url = url,
                    destination = destination,
                    expectedSha1 = expectedSha1
                )
            ) {

                is DownloadResult.Success -> {
                    result.add(destination)
                }

                is DownloadResult.Error -> {
                    // Library failed to download.
                }
            }
        }

        return result
    }
}
