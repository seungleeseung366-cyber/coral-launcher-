package com.coral.launcher.renderer

interface RendererProvider {
    val info: RendererInfo
    fun isAvailable(): Boolean
    fun prepare(): Boolean
    fun environment(): Map<String, String>
    fun release()
}
