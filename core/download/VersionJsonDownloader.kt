package com.coral.launcher.core.download

import java.io.File
import java.net.HttpURLConnection
import java.net.URL

class VersionJsonDownloader {

    fun download(
        url: String,
        destination: File
    ): Boolean {
        return try {
            destination.parentFile?.mkdirs()

            val connection =
                URL(url).openConnection() as HttpURLConnection

            connection.requestMethod = "GET"
            connection.connectTimeout = 15000
            connection.readTimeout = 30000

            try {
                if (connection.responseCode !in 200..299) {
                    return false
                }

                connection.inputStream.use { input ->
                    destination.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }

                destination.isFile &&
                    destination.length() > 0
            } finally {
                connection.disconnect()
            }
        } catch (_: Exception) {
            false
        }
    }
}
