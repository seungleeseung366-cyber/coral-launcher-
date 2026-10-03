package com.coral.launcher.core.game

import java.io.File

object LibraryPathResolver {

    fun resolve(
        librariesDirectory: File,
        path: String
    ): File {
        return File(librariesDirectory, path)
    }

    fun exists(
        librariesDirectory: File,
        path: String
    ): Boolean {
        return resolve(librariesDirectory, path).isFile
    }
}
