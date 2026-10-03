package com.coral.launcher.core.game

import com.coral.launcher.core.download.AssetIndexDownloader
import com.coral.launcher.core.download.AssetInstallService
import com.coral.launcher.core.download.AssetObjectDownloader
import com.coral.launcher.core.download.ClientDownloader
import com.coral.launcher.core.download.FileDownloader
import com.coral.launcher.core.download.LibraryInstallService
import org.json.JSONObject
import java.io.File
import java.net.HttpURLConnection
import java.net.URL

class MinecraftInstallService(
    private val gameDirectory: File
) {

    fun install(
        metadata: VersionMetadata
    ): Boolean {
        val planner = VersionInstallPlanner(gameDirectory)
        val plan = planner.createPlan(metadata)

        plan.versionDirectory.mkdirs()
        plan.librariesDirectory.mkdirs()
        plan.assetsDirectory.mkdirs()

        val fileDownloader = FileDownloader()

        // 1. Download client JAR
        val clientUrl = metadata.clientDownloadUrl
            ?: return false

        val clientDownloader = ClientDownloader(
            fileDownloader
        )

        if (!clientDownloader.download(
                url = clientUrl,
                destination = plan.clientJar,
                expectedSha1 = metadata.clientSha1
            )
        ) {
            return false
        }

        // 2. Download libraries
        val librariesJson = fetchLibraries(metadata.id)
            ?: return false

        val libraryService = LibraryInstallService(
            librariesDirectory = plan.librariesDirectory,
            fileDownloader = fileDownloader
        )

        if (!libraryService.install(librariesJson)) {
            return false
        }

        // 3. Download asset index
        val assetIndexUrl = metadata.assetIndexUrl

        if (!assetIndexUrl.isNullOrBlank()) {
            if (!AssetIndexDownloader().download(
                    url = assetIndexUrl,
                    destination = plan.assetIndexFile
                )
            ) {
                return false
            }

            // 4. Download asset objects
            val assetIndex = JSONObject(
                plan.assetIndexFile
                    .readText()
            )

            val assetService = AssetInstallService(
                assetsDirectory = plan.assetsDirectory,
                assetObjectDownloader = AssetObjectDownloader()
            )

            if (!assetService.install(assetIndex)) {
                return false
            }
        }

        return true
    }

    private fun fetchLibraries(
        versionId: String
    ): org.json.JSONArray? {
        return try {
            val versionJson = File(
                gameDirectory,
                "versions/$versionId/$versionId.json"
            )

            if (!versionJson.isFile) {
                return null
            }

            val json = JSONObject(
                versionJson.readText()
            )

            json.optJSONArray("libraries")
        } catch (_: Exception) {
            null
        }
    }
}
