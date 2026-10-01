package com.coral.launcher.download

data class DownloadProgress(
    val current: Int = 0,
    val total: Int = 0,
    val currentFile: String = "",
    val completed: Boolean = false,
    val error: String? = null
) {

    val percentage: Int
        get() {
            if (total <= 0) return 0

            return ((current.toFloat() / total) * 100)
                .toInt()
                .coerceIn(0, 100)
        }
}
