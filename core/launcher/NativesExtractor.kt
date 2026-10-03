package com.coral.launcher.core.launcher

import java.io.File
import java.util.zip.ZipFile

class NativesExtractor {

    fun extract(
        nativeJars: List<File>,
        destination: File
    ): Boolean {
        return try {
            destination.mkdirs()

            for (jar in nativeJars) {
                if (!jar.isFile) continue

                ZipFile(jar).use { zip ->
                    val entries = zip.entries()

                    while (entries.hasMoreElements()) {
                        val entry = entries.nextElement()

                        if (entry.isDirectory) continue
                        if (!entry.name.startsWith("META-INF/").not()) continue

                        val output = File(destination, entry.name)

                        val destinationPath =
                            destination.canonicalFile.toPath()

                        val outputPath =
                            output.canonicalFile.toPath()

                        // Mencegah Zip Slip
                        if (!outputPath.startsWith(destinationPath)) {
                            return false
                        }

                        output.parentFile?.mkdirs()

                        zip.getInputStream(entry).use { input ->
                            output.outputStream().use { out ->
                                input.copyTo(out)
                            }
                        }
                    }
                }
            }

            true
        } catch (_: Exception) {
            false
        }
    }
}
