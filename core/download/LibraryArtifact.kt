package com.coral.launcher.download

import java.io.File

data class LibraryArtifact(
    val name: String,
    val url: String,
    val path: String,
    val sha1: String?,
    val file: File,
    val isNative: Boolean = false
)
