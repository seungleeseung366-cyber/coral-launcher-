package com.coral.launcher.core.runtime

import java.io.File
import java.util.zip.ZipInputStream

class RuntimeArchiveExtractor {

    fun extract(
        archive: File,
        destination: File
    ): Boolean {
        return try {
            if (!archive.exists()) return false

            destination.mkdirs()

            ZipInputStream(archive.inputStream()).use { zip ->
                var entry = zip.nextEntry

                while (entry != null) {
                    val outputFile = File(destination, entry.name)

                    // Mencegah Zip Slip
                    val destinationPath = destination
                        .canonicalFile
                        .toPath()

                    val outputPath = outputFile
                        .canonicalFile
                        .toPath()

                    if (!outputPath.startsWith(destinationPath)) {
                        return false
                    }

                    if (entry.isDirectory) {
                        outputFile.mkdirs()
                    } else {
                        outputFile.parentFile?.mkdirs()

                        outputFile.outputStream().use { output ->
                            zip.copyTo(output)
                        }
                    }

                    zip.closeEntry()
                    entry = zip.nextEntry
                }
            }

            true
        } catch (_: Exception) {
            false
        }
    }
}
