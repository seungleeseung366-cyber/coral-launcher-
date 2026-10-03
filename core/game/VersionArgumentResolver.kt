package com.coral.launcher.core.game

class VersionArgumentResolver {

    fun resolve(
        arguments: List<String>,
        values: Map<String, String>
    ): List<String> {
        return arguments.mapNotNull { argument ->
            var result = argument

            values.forEach { (key, value) ->
                result = result.replace(
                    "\${$key}",
                    value
                )
            }

            if (result.contains("\${")) {
                null
            } else {
                result
            }
        }
    }
}
