package com.coral.launcher.launcher

import android.content.Context
import com.coral.launcher.game.GamePaths
import java.io.File

class LauncherEngine(
    private val context: Context
) {

    fun prepare(): LaunchResult {
        return try {
            GamePaths.prepare(context)

            LaunchResult.Success(
                "CORAL game environment prepared"
            )
        } catch (e: Exception) {
            LaunchResult.Error(
                "Failed to prepare game environment",
                e
            )
        }
    }

    fun buildLaunchDirectory(
        versionId: String
    ): File {
        val directory = GamePaths.version(
            context,
            versionId
        )

        directory.mkdirs()

        return directory
    }

    fun buildJavaArguments(
        config: LaunchConfig
    ): List<String> {
        return listOf(
            "-Xms${config.minMemoryMb}M",
            "-Xmx${config.maxMemoryMb}M"
        )
    }

    fun buildEnvironment(
        config: LaunchConfig
    ): Map<String, String> {
        return mapOf(
            "CORAL_RENDERER" to config.renderer,
            "CORAL_VERSION" to config.versionId
        )
    }
}
