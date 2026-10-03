package com.coral.launcher.core.game

import com.coral.launcher.core.download.AssetIndexDownloader
import com.coral.launcher.core.download.ClientDownloader
import java.io.File

class VersionInstallService(
    private val gameDirectory: File,
    private val clientDownloader: ClientDownloader,
    private val assetIndexDownloader: AssetIndexDownloader
) {

    private val planner = VersionInstallPlanner(gameDirectory)

    fun install(
        metadata: VersionMetadata
    ): Boolean {
        val plan = planner.createPlan(metadata)

        plan.versionDirectory.mkdirs()
        plan.librariesDirectory.mkdirs()
        plan.assetsDirectory.mkdirs()
        plan.assetIndexFile.parentFile?.mkdirs()

        val clientUrl = metadata.clientDownloadUrl
            ?: return false

        val clientDownloaded = clientDownloader.download(
            url = clientUrl,
            destination = plan.clientJar,
            expectedSha1 = metadata.clientSha1
        )

        if (!clientDownloaded) {
            return false
        }

        val assetUrl = metadata.assetIndexUrl

        if (!assetUrl.isNullOrBlank()) {
            val assetIndexDownloaded =
                assetIndexDownloader.download(
                    url = assetUrl,
                    destination = plan.assetIndexFile
                )

            if (!assetIndexDownloaded) {
                return false
            }
        }

        return true
    }
}
