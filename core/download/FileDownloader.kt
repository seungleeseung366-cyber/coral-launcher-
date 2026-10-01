package com.coral.launcher.download

import java.io.File
import java.net.HttpURLConnection
import java.net.URL

object FileDownloader {

    fun download(
        url: String,
        destination: File
    ): DownloadResult {

        return try {
            destination.parentFile?.mkdirs()

            val connection =
                URL(url).openConnection() as HttpURLConnection

            connection.requestMethod = "GET"
            connection.connectTimeout = 15000
            connection.readTimeout = 30000
            connection.setRequestProperty(
                "User-Agent",
                "CORAL-Launcher/0.1"
            )

            try {
                if (connection.responseCode !in 200..299) {
                    return DownloadResult.Error(
                        "Download failed: HTTP ${connection.responseCode}"
                    )
                }

                connection.inputStream.use { input ->
                    destination.outputStream().use { output ->

                        val buffer = ByteArray(8192)

                        while (true) {
                            val count = input.read(buffer)

                            if (count == -1) {
                                break
                            }

                            output.write(
                                buffer,
                                0,
                                count
                            )
                        }
                    }
                }

                DownloadResult.Success(destination)

            } finally {
                connection.disconnect()
            }

        } catch (e: Exception) {

            DownloadResult.Error(
                message = e.message
                    ?: "Unknown download error",
                cause = e
            )
        }
    }
}
