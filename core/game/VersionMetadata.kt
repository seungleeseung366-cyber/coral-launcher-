package com.coral.launcher.core.game

data class VersionMetadata(
    val id: String,
    val type: String,
    val mainClass: String,
    val clientDownloadUrl: String?,
    val clientSha1: String?,
    val assetIndexId: String?,
    val assetIndexUrl: String?,
    val libraries: List<LibraryInfo>,
    val arguments: List<String>,
    val jvmArguments: List<String>
)

data class LibraryInfo(
    val name: String,
    val url: String,
    val sha1: String?,
    val path: String?
)
