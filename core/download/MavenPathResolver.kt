package com.coral.launcher.download

object MavenPathResolver {

    fun toPath(
        coordinate: String
    ): String? {

        val parts =
            coordinate.split(":")

        if (parts.size < 3) {
            return null
        }

        val group =
            parts[0]

        val name =
            parts[1]

        val version =
            parts[2]

        val groupPath =
            group.replace(
                ".",
                "/"
            )

        return "$groupPath/$name/$version/$name-$version.jar"
    }

    fun fileName(
        coordinate: String
    ): String? {

        val parts =
            coordinate.split(":")

        if (parts.size < 3) {
            return null
        }

        val name =
            parts[1]

        val version =
            parts[2]

        return "$name-$version.jar"
    }
}
