package com.coral.launcher.core.runtime

import java.io.File

data class JavaRuntime(
    val javaHome: File,
    val javaBinary: File,
    val version: String
) {
    fun isValid(): Boolean {
        return javaHome.exists() &&
                javaHome.isDirectory &&
                javaBinary.exists() &&
                javaBinary.isFile
    }
}
