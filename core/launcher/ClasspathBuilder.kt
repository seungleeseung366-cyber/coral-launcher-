package com.coral.launcher.core.launcher

import java.io.File

class ClasspathBuilder {

    fun build(
        libraries: List<File>,
        minecraftJar: File
    ): List<File> {
        val classpath = mutableListOf<File>()

        libraries
            .filter { it.isFile && it.extension == "jar" }
            .forEach { classpath.add(it) }

        if (minecraftJar.isFile) {
            classpath.add(minecraftJar)
        }

        return classpath.distinctBy { it.absolutePath }
    }
}
