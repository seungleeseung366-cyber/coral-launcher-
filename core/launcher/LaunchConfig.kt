package com.coral.launcher.launcher

data class LaunchConfig(
    val versionId: String,
    val username: String = "Player",
    val minMemoryMb: Int = 512,
    val maxMemoryMb: Int = 2048,
    val renderer: String = "auto"
)
