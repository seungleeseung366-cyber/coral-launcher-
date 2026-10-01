package com.coral.launcher.launcher

sealed class LaunchResult {

    data class Success(
        val message: String
    ) : LaunchResult()

    data class Error(
        val message: String,
        val cause: Throwable? = null
    ) : LaunchResult()
}
