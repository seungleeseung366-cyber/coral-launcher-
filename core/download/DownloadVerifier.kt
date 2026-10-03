package com.coral.launcher.core.download

import java.io.File
import java.security.MessageDigest

object DownloadVerifier {

    fun sha1(
        file: File
    ): String {
        val digest = MessageDigest.getInstance("SHA-1")

        file.inputStream().use { input ->
            val buffer = ByteArray(8192)

            while (true) {
                val read = input.read(buffer)

                if (read == -1) break

                digest.update(buffer, 0, read)
            }
        }

        return digest.digest()
            .joinToString("") {
                "%02x".format(it)
            }
    }

    fun verifySha1(
        file: File,
        expectedSha1: String
    ): Boolean {
        if (!file.isFile) return false

        return try {
            sha1(file).equals(
                expectedSha1,
                ignoreCase = true
            )
        } catch (_: Exception) {
            false
        }
    }
}
