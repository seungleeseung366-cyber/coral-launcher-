package com.coral.launcher.core.launcher

import java.io.File

data class GameLaunchRequest(
    val javaBinary: File,
    val gameDirectory: File,
    val minecraftJar: File,
    val libraries: List<File>,
    val mainClass: String,
    val jvmArguments: List<String> = emptyList(),
    val gameArguments: List<String> = emptyList(),
    val nativesDirectory: File? = null
)
