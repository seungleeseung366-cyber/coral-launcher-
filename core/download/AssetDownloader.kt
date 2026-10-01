package com.coral.launcher.download

import android.content.Context
import com.coral.launcher.game.GamePaths
import org.json.JSONObject
import java.io.File

class AssetDownloader(
    private val context: Context
) {

    fun downloadAssets(
        versionJson: File
    ): List<File> {

        val result = mutableListOf<File>()

        val json = JSONObject(
            versionJson.readText()
        )

        val assetIndexObject =
            json.optJSONObject("assetIndex")
            ?: return result

        val assetIndexUrl =
            assetIndexObject.optString("url")

        val assetIndexId =
            assetIndexObject.optString("id")

        if (
            assetIndexUrl.isBlank() ||
            assetIndexId.isBlank()
        ) {
            return result
        }

        val indexesDir = File(
            GamePaths.assets(context),
            "indexes"
        )

        indexesDir.mkdirs()

        val indexFile = File(
            indexesDir,
            "$assetIndexId.json"
        )

        when (
            FileDownloader.download(
                assetIndexUrl,
                indexFile
            )
        ) {
            is DownloadResult.Error -> {
                return result
            }

            is DownloadResult.Success -> {
                // Continue
            }
        }

        val indexJson = JSONObject(
            indexFile.readText()
        )

        val objects =
            indexJson.optJSONObject("objects")
            ?: return result

        val objectsDir = File(
            GamePaths.assets(context),
            "objects"
        )

        objectsDir.mkdirs()

        val keys = objects.keys()

        while (keys.hasNext()) {

            val key = keys.next()

            val asset =
                objects.getJSONObject(key)

            val hash =
                asset.optString("hash")

            if (hash.length < 2) {
                continue
            }

            val prefix = hash.substring(0, 2)

            val destination = File(
                objectsDir,
                "$prefix/$hash"
            )

            val url =
                "https://resources.download.minecraft.net/$prefix/$hash"

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
                    // Asset gagal di-download.
                    // Akan ditangani lebih detail
                    // pada downloader berikutnya.
                }
            }
        }

        return result
    }
}
