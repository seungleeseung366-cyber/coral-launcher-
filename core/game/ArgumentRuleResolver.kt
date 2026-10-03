package com.coral.launcher.core.game

import org.json.JSONArray
import org.json.JSONObject

object ArgumentRuleResolver {

    fun resolve(
        arguments: JSONArray?,
        features: Set<String> = emptySet()
    ): List<String> {
        if (arguments == null) return emptyList()

        val result = mutableListOf<String>()
        val evaluator = RuleEvaluator(
            osName = "linux",
            features = features
        )

        for (i in 0 until arguments.length()) {
            when (val item = arguments.opt(i)) {
                is String -> {
                    result.add(item)
                }

                is JSONObject -> {
                    val rules = item.optJSONArray("rules")

                    if (!evaluator.isAllowed(rules)) {
                        continue
                    }

                    val value = item.opt("value")

                    when (value) {
                        is String -> result.add(value)

                        is JSONArray -> {
                            for (j in 0 until value.length()) {
                                value.optString(j)
                                    .takeIf { it.isNotEmpty() }
                                    ?.let { result.add(it) }
                            }
                        }
                    }
                }
            }
        }

        return result
    }
}
