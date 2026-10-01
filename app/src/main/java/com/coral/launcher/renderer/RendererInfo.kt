package com.coral.launcher.renderer

data class RendererInfo(
    val id: String,
    val name: String,
    val description: String,
    val available: Boolean = false,
    val requiresVulkan: Boolean = false,
    val requiresOpenGLES: Boolean = false
)
