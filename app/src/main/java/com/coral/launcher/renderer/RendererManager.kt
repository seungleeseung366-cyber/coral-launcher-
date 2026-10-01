package com.coral.launcher.renderer

class RendererManager {
    private val providers = listOf(
        StubRenderer("auto", "Auto", "Automatically select a compatible renderer."),
        StubRenderer("gl4es", "GL4ES", "OpenGL compatibility renderer."),
        StubRenderer("mobileglues", "MobileGlues", "Mobile OpenGL compatibility layer."),
        StubRenderer("zink", "Zink", "OpenGL implementation over Vulkan."),
        StubRenderer("angle", "ANGLE", "Graphics translation layer."),
        StubRenderer("vulkan", "Vulkan", "Vulkan graphics backend."),
        StubRenderer("virgl", "VirGL", "Virtualized OpenGL renderer.")
    )

    fun all(): List<RendererProvider> = providers

    fun available(): List<RendererProvider> =
        providers.filter { it.isAvailable() }

    fun find(id: String): RendererProvider? =
        providers.firstOrNull { it.info.id == id }

    fun select(id: String): RendererProvider? = find(id)
}

private class StubRenderer(
    id: String,
    name: String,
    description: String
) : RendererProvider {

    override val info = RendererInfo(
        id = id,
        name = name,
        description = description,
        available = false
    )

    override fun isAvailable(): Boolean = false

    override fun prepare(): Boolean = false

    override fun environment(): Map<String, String> =
        mapOf("CORAL_RENDERER" to info.id)

    override fun release() {}
}
