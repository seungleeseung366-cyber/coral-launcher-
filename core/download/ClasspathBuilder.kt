package com.coral.launcher.launcher

import com.coral.launcher.download.VersionDownloadInfo
import java.io.File

object ClasspathBuilder {

    fun build(
        info: VersionDownloadInfo
    ): List<File> {

        val classpath =
            mutableListOf<File>()

        info.clientJar?.let {
            if (it.exists() && it.isFile) {
                classpath.add(it)
            }
        }

        for (library in info.libraries) {
            if (
                library.exists() &&
                library.isFile
            ) {
                classpath.add(library)
            }
        }

        return classpath.distinctBy {
            it.absolutePath
        }
    }

    fun buildString(
        info: VersionDownloadInfo
    ): String {

        return build(info)
            .joinToString(
                separator = File.pathSeparator
            ) {
                it.absolutePath
            }
    }
}
