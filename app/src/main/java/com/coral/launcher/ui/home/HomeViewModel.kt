package com.coral.launcher.ui.home

import androidx.lifecycle.ViewModel
import com.coral.launcher.game.MinecraftVersion
import com.coral.launcher.game.VersionManifestService

class HomeViewModel : ViewModel() {

    var versions: List<MinecraftVersion> = emptyList()
        private set

    var loading: Boolean = false
        private set

    var error: String? = null
        private set

    fun loadVersions(
        onFinished: () -> Unit
    ) {
        if (loading) return

        loading = true
        error = null

        Thread {
            try {
                versions = VersionManifestService.fetch()
            } catch (e: Exception) {
                error = e.message
                    ?: "Failed to load Minecraft versions"
            }

            loading = false
            onFinished()
        }.start()
    }
}
