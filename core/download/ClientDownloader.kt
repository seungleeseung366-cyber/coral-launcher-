package com.coral.launcher.core.download

import java.io.File

class ClientDownloader(
    private val fileDownloader: FileDownloader
) {

    fun download(
        url: String,
        destination: File,
        expectedSha1: String?
    ): Boolean {
        if (destination.isFile && destination.length() > 0) {
            if (expectedSha1.isNullOrBlank()) {
                return true
            }

            if (DownloadVerifier.verifySha1(destination, expectedSha1)) {
                return true
            }

            destination.delete()
        }

        val result = fileDownloader.download(
            url,
            destination
        )

        if (!result) {
            return false
        }

        return if (expectedSha1.isNullOrBlank()) {
            destination.isFile
        } else {
            DownloadVerifier.verifySha1(
                destination,
                expectedSha1
            )
        }
    }
}
