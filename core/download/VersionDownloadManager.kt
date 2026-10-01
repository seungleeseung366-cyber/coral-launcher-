package com.coral.launcher.download

import android.content.Context
import com.coral.launcher.game.MinecraftVersion
import java.io.File

class VersionDownloadManager(
    context: Context
) {

    private val minecraftDownloader =
        MinecraftDownloader(context)

    private val libraryDownloader =
        LibraryDownloader(context)

    private val assetDownloader =
        AssetDownloader(context)

    fun download(
        version: MinecraftVersion
    ): VersionDownloadResult {

        val downloadedFiles =
            mutableListOf<File>()

        // 1. Version JSON
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
                    return VersionDownloadResult.Error(
                        version.id,
                        versionResult.message,
                        downloadedFiles
                    )
                }
            }

        // 2. Client JAR
        val clientResult =
            minecraftDownloader.downloadClient(
                version,
                versionJson
            )

        val clientJar =
            when (clientResult) {

                is DownloadResult.Success -> {
                    downloadedFiles.add(
                        clientResult.file
                    )

                    clientResult.file
                }

                is DownloadResult.Error -> {
                    return VersionDownloadResult.Error(
                        version.id,
                        clientResult.message,
                        downloadedFiles
                    )
                }
            }

        // 3. Libraries
        val libraries =
            libraryDownloader.downloadLibraries(
                version.id,
                versionJson
            )

        downloadedFiles.addAll(libraries)

        // 4. Assets
        val assets =
            assetDownloader.downloadAssets(
                versionJson
            )

        downloadedFiles.addAll(assets)

        return VersionDownloadResult.Success(
            info = VersionDownloadInfo(
                versionId = version.id,
                versionJson = versionJson,
                clientJar = clientJar,
                libraries = libraries,
                assets = assets
            )
        )
    }
}

sealed class VersionDownloadResult {

    data class Success(
        val info: VersionDownloadInfo
    ) : VersionDownloadResult()

    data class Error(
        val versionId: String,
        val message: String,
        val downloadedFiles: List<File>
    ) : VersionDownloadResult()
}
