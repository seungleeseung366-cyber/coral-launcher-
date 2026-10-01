package com.coral.launcher.game

import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

object VersionManifestService {

    private const val MANIFEST_URL =
        "https://piston-meta.mojang.com/mc/game/version_manifest_v2.json"

    fun fetch(): List<MinecraftVersion> {
        val connection =
            URL(MANIFEST_URL).openConnection() as HttpURLConnection

        connection.requestMethod = "GET"
        connection.connectTimeout = 15000
        connection.readTimeout = 15000
        connection.setRequestProperty(
            "User-Agent",
            "CORAL-Launcher/0.1"
        )

        try {
            if (connection.responseCode !in 200..299) {
                throw Exception("HTTP ${connection.responseCode}")
            }

            val json = connection.inputStream
                .bufferedReader()
                .use { it.readText() }

            val root = JSONObject(json)
            val versions = root.getJSONArray("versions")

            val result = ArrayList<MinecraftVersion>()

            for (i in 0 until versions.length()) {
                val item = versions.getJSONObject(i)

                result.add(
                    MinecraftVersion(
                        id = item.getString("id"),
                        type = item.optString(
                            "type",
                            "unknown"
                        ),
                        url = item.getString("url"),
                        releaseTime = item.optString(
                            "releaseTime",
                            ""
                        ),
                        sha1 = item.optString(
                            "sha1",
                            ""
                        )
                    )
                )
            }

            return result
        } finally {
            connection.disconnect()
        }
    }
}
