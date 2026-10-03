package com.coral.launcher.core.download

import java.io.File
import java.security.MessageDigest

object ClientJarVerifier {

    fun verify(
        file: File,
        expectedSha1: String?
    ): Boolean {
        if (!file.isFile) return false

        if (expectedSha1.isNullOrBlank()) {
            return file.length() > 0
        }

        return try {
            val digest = MessageDigest.getInstance("SHA-1")

            file.inputStream().use { input ->
                val buffer = ByteArray(8192)

                while (true) {
                    val read = input.read(buffer)

                    if (read == -1) break

                    digest.update(buffer, 0, read)
                }
            }

            val actualSha1 = digest.digest()
                .joinToString("") {
                    "%02x".format(it)
                }

            actualSha1.equals(
                expectedSha1,
                ignoreCase = true
            )
        } catch (_: Exception) {
            false
        }
    }
}
