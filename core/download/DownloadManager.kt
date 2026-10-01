package com.coral.launcher.download

import android.content.Context
import com.coral.launcher.game.MinecraftVersion
import java.io.File

class DownloadManager(
    private val context: Context
) {

    private val minecraftDownloader =
        MinecraftDownloader(context)

    private val libraryDownloader =
        LibraryDownloader(context)

    private val assetDownloader =
        AssetDownloader(context)

    fun downloadVersion(
        version: MinecraftVersion
    ): DownloadSummary {

        val downloadedFiles =
            mutableListOf<File>()

        // 1. Download version JSON
        val versionResult =
            minecraftDownloader.downloadVersion(version)

        val versionJson =
            when (versionResult) {

                is DownloadResult.Success -> {
                    downloadedFiles.add(
                        versionResult.file
                    )

                    versionResult.file
                }

                is DownloadResult.Error -> {
                    return DownloadSummary(
                        success = false,
                        files = downloadedFiles,
                        error = versionResult.message
                    )
                }
            }

        // 2. Download client JAR
        when (
            val result =
                minecraftDownloader.downloadClient(
                    version,
                    versionJson
                )
        ) {
            is DownloadResult.Success -> {
                downloadedFiles.add(
                    result.file
                )
            }

            is DownloadResult.Error -> {
                return DownloadSummary(
                    success = false,
                    files = downloadedFiles,
                    error = result.message
                )
            }
        }

        // 3. Download libraries
        downloadedFiles.addAll(
            libraryDownloader.downloadLibraries(
                version.id,
                versionJson
            )
        )

        // 4. Download assets
        downloadedFiles.addAll(
            assetDownloader.downloadAssets(
                versionJson
            )
        )

        return DownloadSummary(
            success = true,
            files = downloadedFiles,
            error = null
        )
    }
}

data class DownloadSummary(
    val success: Boolean,
    val files: List<File>,
    val error: String? = null
)
