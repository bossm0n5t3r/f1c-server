package me.f1c.util

import java.io.File

object FileUtil {
    private const val FILE_LOCATION = "data"

    private fun String.asInputStream() = File("$FILE_LOCATION/$this").inputStream()

    fun String.readText() = this.asInputStream().bufferedReader().readText()
}
