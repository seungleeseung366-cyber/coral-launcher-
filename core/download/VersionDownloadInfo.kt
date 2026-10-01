package com.coral.launcher.download

import java.io.File

data class VersionDownloadInfo(
    val versionId: String,
    val versionJson: File,
    val clientJar: File?,
    val libraries: List<File>,
    val assets: List<File>
)
