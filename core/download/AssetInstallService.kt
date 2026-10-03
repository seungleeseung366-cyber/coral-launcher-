package com.coral.launcher.core.download

import org.json.JSONObject
import java.io.File

class AssetInstallService(
    private val assetsDirectory: File,
    private val assetObjectDownloader: AssetObjectDownloader
) {

    fun install(
        assetIndex: JSONObject
    ): Boolean {
        val objects = AssetIndexParser.parse(assetIndex)

        for (asset in objects) {
            val destination = File(
                assetsDirectory,
                "objects/${asset.hash.take(2)}/${asset.hash}"
            )

            if (destination.isFile &&
                AssetVerifier.verify(
                    destination,
                    asset.hash
                )
            ) {
                continue
            }

            if (destination.exists()) {
                destination.delete()
            }

            val downloaded = assetObjectDownloader.download(
                hash = asset.hash,
                assetsDirectory = File(
                    assetsDirectory,
                    "objects"
                )
            )

            if (!downloaded) {
                return false
            }

            if (!AssetVerifier.verify(
                    destination,
                    asset.hash
                )
            ) {
                destination.delete()
                return false
            }
        }

        return true
    }
}
