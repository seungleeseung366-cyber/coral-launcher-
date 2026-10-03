package com.coral.launcher.core.game

import org.json.JSONArray
import org.json.JSONObject

object VersionMetadataParser {

    fun parse(json: JSONObject): VersionMetadata {
        val downloads = json.optJSONObject("downloads")
        val client = downloads?.optJSONObject("client")

        val assetIndex = json.optJSONObject("assetIndex")

        return VersionMetadata(
            id = json.optString("id"),
            type = json.optString("type", "release"),
            mainClass = json.optString("mainClass"),
            clientDownloadUrl = client?.optString("url"),
            clientSha1 = client?.optString("sha1"),
            assetIndexId = assetIndex?.optString("id"),
            assetIndexUrl = assetIndex?.optString("url"),
            libraries = parseLibraries(
                json.optJSONArray("libraries")
            ),
            arguments = parseArguments(
                json.optJSONObject("arguments")
                    ?.optJSONArray("game")
            ),
            jvmArguments = parseArguments(
                json.optJSONObject("arguments")
                    ?.optJSONArray("jvm")
            )
        )
    }

    private fun parseLibraries(
        array: JSONArray?
    ): List<LibraryInfo> {
        if (array == null) return emptyList()

        val result = mutableListOf<LibraryInfo>()

        for (i in 0 until array.length()) {
            val library = array.optJSONObject(i) ?: continue
            val downloads = library.optJSONObject("downloads")
            val artifact = downloads?.optJSONObject("artifact")

            result.add(
                LibraryInfo(
                    name = library.optString("name"),
                    url = artifact?.optString("url").orEmpty(),
                    sha1 = artifact?.optString("sha1"),
                    path = artifact?.optString("path")
                )
            )
        }

        return result
    }

    private fun parseArguments(
        array: JSONArray?
    ): List<String> {
        if (array == null) return emptyList()

        val result = mutableListOf<String>()

        for (i in 0 until array.length()) {
            when (val value = array.opt(i)) {
                is String -> result.add(value)

                is JSONObject -> {
                    val valueArray = value.optJSONArray("value")

                    if (valueArray != null) {
                        for (j in 0 until valueArray.length()) {
                            result.add(
                                valueArray.optString(j)
                            )
                        }
                    } else {
                        value.optString("value")
                            .takeIf { it.isNotEmpty() }
                            ?.let { result.add(it) }
                    }
                }
            }
        }

        return result
    }
}
