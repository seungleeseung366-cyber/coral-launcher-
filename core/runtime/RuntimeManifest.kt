package com.coral.launcher.core.runtime

data class RuntimeManifest(
    val version: String,
    val downloadUrl: String,
    val sha256: String? = null
)
