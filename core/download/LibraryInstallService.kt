package com.coral.launcher.core.download

import com.coral.launcher.core.game.LibraryRuleResolver
import org.json.JSONArray
import java.io.File

class LibraryInstallService(
    private val librariesDirectory: File,
    private val fileDownloader: FileDownloader
) {

    fun install(libraries: JSONArray): Boolean {
        val allowedLibraries =
            LibraryRuleResolver.getAllowedLibraries(libraries)

        for (library in allowedLibraries) {
            val downloads = library.optJSONObject("downloads")
                ?: continue

            val artifact = downloads.optJSONObject("artifact")
                ?: continue

            val url = artifact.optString("url")
            val path = artifact.optString("path")
            val sha1 = artifact.optString("sha1")

            if (url.isBlank() || path.isBlank()) {
                continue
            }

            val destination = File(
                librariesDirectory,
                path
            )

            destination.parentFile?.mkdirs()

            if (destination.isFile &&
                (sha1.isBlank() ||
                    LibraryVerifier.verify(destination, sha1))
            ) {
                continue
            }

            if (destination.exists()) {
                destination.delete()
            }

            val downloaded = fileDownloader.download(
                url,
                destination
            )

            if (!downloaded) {
                return false
            }

            if (sha1.isNotBlank() &&
                !LibraryVerifier.verify(destination, sha1)
            ) {
                destination.delete()
                return false
            }
        }

        return true
    }
}
