package com.coral.launcher.core.download

import java.io.File
import java.net.HttpURLConnection
import java.net.URL

class AssetObjectDownloader {

    fun download(
        hash: String,
        assetsDirectory: File
    ): Boolean {
        if (hash.length < 2) return false

        val prefix = hash.substring(0, 2)

        val destination = File(
            assetsDirectory,
            "$prefix/$hash"
        )

        if (destination.isFile && destination.length() > 0) {
            return true
        }

        return try {
            destination.parentFile?.mkdirs()

            val url = URL(
                "https://resources.download.minecraft.net/$prefix/$hash"
            )

            val connection =
                url.openConnection() as HttpURLConnection

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

                destination.isFile && destination.length() > 0
            } finally {
                connection.disconnect()
            }
        } catch (_: Exception) {
            false
        }
    }
}
