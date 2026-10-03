package com.coral.launcher.core.game

import org.json.JSONArray
import org.json.JSONObject

object LibraryRuleResolver {

    fun isAllowed(library: JSONObject): Boolean {
        val rules = library.optJSONArray("rules")

        return RuleEvaluator(
            osName = "linux"
        ).isAllowed(rules)
    }

    fun getAllowedLibraries(
        libraries: JSONArray
    ): List<JSONObject> {
        val result = mutableListOf<JSONObject>()

        for (i in 0 until libraries.length()) {
            val library = libraries.optJSONObject(i) ?: continue

            if (isAllowed(library)) {
                result.add(library)
            }
        }

        return result
    }
}
