package com.coral.launcher.core.download

import org.json.JSONObject

data class AssetObject(
    val name: String,
    val hash: String,
    val size: Long
)

object AssetIndexParser {

    fun parse(json: JSONObject): List<AssetObject> {
        val objects = json.optJSONObject("objects")
            ?: return emptyList()

        val result = mutableListOf<AssetObject>()
        val keys = objects.keys()

        while (keys.hasNext()) {
            val name = keys.next()
            val objectData = objects.optJSONObject(name) ?: continue

            val hash = objectData.optString("hash")
            val size = objectData.optLong("size", 0L)

            if (hash.isNotEmpty()) {
                result.add(
                    AssetObject(
                        name = name,
                        hash = hash,
                        size = size
                    )
                )
            }
        }

        return result
    }
}
