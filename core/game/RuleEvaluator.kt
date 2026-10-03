package com.coral.launcher.core.game

import org.json.JSONArray
import org.json.JSONObject

class RuleEvaluator(
    private val osName: String = "android",
    private val features: Set<String> = emptySet()
) {

    fun isAllowed(rules: JSONArray?): Boolean {
        if (rules == null || rules.length() == 0) {
            return true
        }

        var allowed = false

        for (i in 0 until rules.length()) {
            val rule = rules.optJSONObject(i) ?: continue

            if (!matches(rule)) {
                continue
            }

            allowed = when (rule.optString("action")) {
                "allow" -> true
                "disallow" -> false
                else -> allowed
            }
        }

        return allowed
    }

    private fun matches(rule: JSONObject): Boolean {
        val os = rule.optJSONObject("os")

        if (os != null) {
            val ruleName = os.optString("name")

            if (ruleName.isNotEmpty() &&
                ruleName != osName &&
                !(osName == "android" && ruleName == "linux")
            ) {
                return false
            }
        }

        val ruleFeatures = rule.optJSONObject("features")

        if (ruleFeatures != null) {
            val keys = ruleFeatures.keys()

            while (keys.hasNext()) {
                val feature = keys.next()
                val required = ruleFeatures.optBoolean(feature)

                if (features.contains(feature) != required) {
                    return false
                }
            }
        }

        return true
    }
}
