package me.f1c.util

import org.springframework.core.io.ClassPathResource

object FileUtil {
    private const val FILE_LOCATION = "data"

    private fun String.asInputStream() = ClassPathResource("$FILE_LOCATION/$this").inputStream

    fun String.readText() = this.asInputStream().bufferedReader().readText()
}
