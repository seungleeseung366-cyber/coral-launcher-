package com.coral.launcher.storage

import android.content.Context
import com.coral.launcher.game.GamePaths
import java.io.File

object CoralStorage {

    fun initialize(context: Context): File {
        GamePaths.prepare(context)
        return GamePaths.minecraft(context)
    }

    fun getLogsDirectory(context: Context): File {
        val logs = File(
            GamePaths.root(context),
            "logs"
        )

        logs.mkdirs()

        return logs
    }

    fun writeLog(
        context: Context,
        message: String
    ) {
        val file = File(
            getLogsDirectory(context),
            "coral.log"
        )

        file.appendText(
            "[${System.currentTimeMillis()}] $message\n"
        )
    }
}
