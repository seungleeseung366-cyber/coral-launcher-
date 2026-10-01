package com.coral.launcher.download

sealed class DownloadState {

    data object Idle : DownloadState()

    data class Downloading(
        val versionId: String,
        val progress: DownloadProgress
    ) : DownloadState()

    data class Success(
        val info: VersionDownloadInfo
    ) : DownloadState()

    data class Error(
        val versionId: String,
        val message: String
    ) : DownloadState()
}
