package com.coral.launcher.download

import java.io.File
import java.security.MessageDigest

object DownloadValidator {

    fun exists(
        file: File
    ): Boolean {
        return file.exists() &&
                file.isFile &&
                file.length() > 0
    }

    fun sha1(
        file: File
    ): String? {

        if (!exists(file)) {
            return null
        }

        return try {
            val digest =
                MessageDigest.getInstance("SHA-1")

            file.inputStream().use { input ->

                val buffer = ByteArray(8192)

                while (true) {

                    val count =
                        input.read(buffer)

                    if (count == -1) {
                        break
                    }

                    digest.update(
                        buffer,
                        0,
                        count
                    )
                }
            }

            digest.digest()
                .joinToString("") {
                    "%02x".format(it)
                }

        } catch (_: Exception) {
            null
        }
    }

    fun verifySha1(
        file: File,
        expectedSha1: String
    ): Boolean {

        if (expectedSha1.isBlank()) {
            return exists(file)
        }

        val actualSha1 =
            sha1(file)
                ?: return false

        return actualSha1.equals(
            expectedSha1,
            ignoreCase = true
        )
    }
}
