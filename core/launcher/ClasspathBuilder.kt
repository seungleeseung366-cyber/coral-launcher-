package com.coral.launcher.core.launcher

import java.io.File

class ClasspathBuilder {

    fun build(
        librariesDirectory: File,
        minecraftJar: File
    ): List<File> {
        val classpath = mutableListOf<File>()

        if (librariesDirectory.exists()) {
            librariesDirectory
                .walkTopDown()
                .filter { it.isFile && it.extension == "jar" }
                .forEach { classpath.add(it) }
        }

        if (minecraftJar.exists()) {
            classpath.add(minecraftJar)
        }

        return classpath.distinctBy { it.absolutePath }
    }
}
