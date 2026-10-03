package com.coral.launcher.core.launcher

import java.io.File

class GameLauncher {

    private val classpathBuilder = ClasspathBuilder()

    fun launch(request: GameLaunchRequest): GameProcess {
        require(request.javaBinary.isFile) {
            "Java binary tidak ditemukan"
        }

        require(request.minecraftJar.isFile) {
            "Minecraft JAR tidak ditemukan"
        }

        require(request.gameDirectory.isDirectory) {
            "Game directory tidak ditemukan"
        }

        val classpath = classpathBuilder.build(
            libraries = request.libraries,
            minecraftJar = request.minecraftJar
        )

        val nativesDirectory = request.nativesDirectory

        val command = buildList {
            add(request.javaBinary.absolutePath)

            if (nativesDirectory != null) {
                add("-Djava.library.path=${nativesDirectory.absolutePath}")
            }

            addAll(request.jvmArguments)

            add("-cp")
            add(
                classpath.joinToString(File.pathSeparator) {
                    it.absolutePath
                }
            )

            add(request.mainClass)
            addAll(request.gameArguments)
        }

        val process = ProcessBuilder(command)
            .directory(request.gameDirectory)
            .redirectErrorStream(true)
            .start()

        return GameProcess(process)
    }
}
