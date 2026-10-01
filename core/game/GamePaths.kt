package com.coral.launcher.game

import android.content.Context
import java.io.File

object GamePaths {

    fun root(context: Context): File {
        return File(context.getExternalFilesDir(null), "coral")
    }

    fun minecraft(context: Context): File {
        return File(root(context), "minecraft")
    }

    fun versions(context: Context): File {
        return File(minecraft(context), "versions")
    }

    fun libraries(context: Context): File {
        return File(minecraft(context), "libraries")
    }

    fun assets(context: Context): File {
        return File(minecraft(context), "assets")
    }

    fun runtime(context: Context): File {
        return File(root(context), "runtime")
    }

    fun version(context: Context, id: String): File {
        return File(versions(context), id)
    }

    fun prepare(context: Context) {
        root(context).mkdirs()
        minecraft(context).mkdirs()
        versions(context).mkdirs()
        libraries(context).mkdirs()
        assets(context).mkdirs()
        runtime(context).mkdirs()
    }
}
