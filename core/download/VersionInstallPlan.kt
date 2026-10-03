package com.coral.launcher.core.game

import java.io.File

data class VersionInstallPlan(
    val versionId: String,
    val versionDirectory: File,
    val clientJar: File,
    val librariesDirectory: File,
    val assetsDirectory: File,
    val assetIndexFile: File
)
