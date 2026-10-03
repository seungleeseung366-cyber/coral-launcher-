package com.coral.launcher.core.runtime

import java.io.File
import java.net.HttpURLConnection
import java.net.URL

class RuntimeDownloader {

    fun download(
        manifest: RuntimeManifest,
        outputFile: File,
        onProgress: ((Int) -> Unit)? = null
    ): Boolean {
        return try {
            outputFile.parentFile?.mkdirs()

            val connection = URL(manifest.downloadUrl)
                .openConnection() as HttpURLConnection

            connection.connectTimeout = 15_000
            connection.readTimeout = 30_000
            connection.requestMethod = "GET"

            connection.connect()

            if (connection.responseCode !in 200..299) {
                connection.disconnect()
                return false
            }

            val totalBytes = connection.contentLengthLong
            var downloadedBytes = 0L

            connection.inputStream.use { input ->
                outputFile.outputStream().use { output ->
                    val buffer = ByteArray(8192)
                    var bytesRead: Int

                    while (input.read(buffer).also { bytesRead = it } != -1) {
                        output.write(buffer, 0, bytesRead)
                        downloadedBytes += bytesRead

                        if (totalBytes > 0) {
                            val progress =
                                ((downloadedBytes * 100) / totalBytes)
                                    .toInt()
                                    .coerceIn(0, 100)

                            onProgress?.invoke(progress)
                        }
                    }
                }
            }

            connection.disconnect()
            true
        } catch (_: Exception) {
            false
        }
    }
}
