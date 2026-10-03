package com.coral.launcher.core.game

import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

object VersionMetadataService {

    fun fetch(version: MinecraftVersion): JSONObject {
        val connection = URL(version.url).openConnection() as HttpURLConnection

        connection.requestMethod = "GET"
        connection.connectTimeout = 15000
        connection.readTimeout = 15000
        connection.setRequestProperty(
            "User-Agent",
            "CORAL-Launcher/0.1"
        )

        try {
            if (connection.responseCode !in 200..299) {
                throw Exception(
                    "HTTP ${connection.responseCode}"
                )
            }

            val json = connection.inputStream
                .bufferedReader()
                .use { it.readText() }

            return JSONObject(json)
        } finally {
            connection.disconnect()
        }
    }
}
