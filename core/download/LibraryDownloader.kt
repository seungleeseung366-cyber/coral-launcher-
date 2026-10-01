package com.coral.launcher.download

import com.coral.launcher.game.GamePaths
import org.json.JSONObject
import android.content.Context
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

        val libraries = json.optJSONArray("libraries")
            ?: return result

        for (i in 0 until libraries.length()) {

            val library = libraries.getJSONObject(i)

            val downloads =
                library.optJSONObject("downloads")
                    ?: continue

            val artifact =
                downloads.optJSONObject("artifact")
                    ?: continue

            val url = artifact.optString("url")
            val path = artifact.optString("path")

            if (url.isBlank() || path.isBlank()) {
                continue
            }

            val destination = File(
                GamePaths.libraries(context),
                path
            )

            when (
                FileDownloader.download(
                    url,
                    destination
                )
            ) {
                is DownloadResult.Success -> {
                    result.add(destination)
                }

                is DownloadResult.Error -> {
                    // Library gagal di-download.
                    // Proses berikutnya akan menangani error
                    // dengan lebih detail.
                }
            }
        }

        return result
    }
}
