package com.coral.launcher.download

sealed class DownloadResult {

    data class Success(
        val file: java.io.File
    ) : DownloadResult()

    data class Error(
        val message: String,
        val cause: Throwable? = null
    ) : DownloadResult()
}
