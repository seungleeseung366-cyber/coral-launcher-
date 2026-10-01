package com.coral.launcher.download

import org.json.JSONArray
import org.json.JSONObject

object LibraryRuleEvaluator {

    fun isAllowed(
        library: JSONObject
    ): Boolean {

        val rules =
            library.optJSONArray("rules")
                ?: return true

        var allowed = true

        for (i in 0 until rules.length()) {

            val rule =
                rules.optJSONObject(i)
                    ?: continue

            val action =
                rule.optString("action")

            val matches =
                matchesRule(rule)

            if (matches) {
                allowed = action == "allow"
            }
        }

        return allowed
    }

    private fun matchesRule(
        rule: JSONObject
    ): Boolean {

        val os =
            rule.optJSONObject("os")

        if (os != null) {

            val name =
                os.optString("name")

            if (
                name.isNotBlank() &&
                !matchesOperatingSystem(name)
            ) {
                return false
            }

            val arch =
                os.optString("arch")

            if (
                arch.isNotBlank() &&
                !matchesArchitecture(arch)
            ) {
                return false
            }
        }

        return true
    }

    private fun matchesOperatingSystem(
        name: String
    ): Boolean {

        val current =
            System.getProperty("os.name")
                ?.lowercase()
                ?: ""

        return when (name.lowercase()) {

            "windows" ->
                current.contains("win")

            "linux" ->
                current.contains("linux")

            "osx" ->
                current.contains("mac")

            else ->
                false
        }
    }

    private fun matchesArchitecture(
        architecture: String
    ): Boolean {

        val current =
            System.getProperty("os.arch")
                ?.lowercase()
                ?: ""

        return when (architecture.lowercase()) {

            "x86" ->
                current.contains("86") &&
                        !current.contains("64")

            "x86_64" ->
                current.contains("x86_64") ||
                        current.contains("amd64")

            "arm64" ->
                current.contains("aarch64") ||
                        current.contains("arm64")

            "arm" ->
                current.contains("arm") &&
                        !current.contains("64")

            else ->
                false
        }
    }
}
