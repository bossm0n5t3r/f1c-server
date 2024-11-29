package me.f1c.port.external

import com.fasterxml.jackson.module.kotlin.readValue
import me.f1c.util.ObjectMapperUtil.objectMapper
import org.springframework.web.client.RestClient

interface ExternalF1Client {
    val restClient: RestClient

    fun getStringResponseOrNull(uri: String): String?
}

inline fun <reified T> ExternalF1Client.callGet(uri: String): T? = this.getStringResponseOrNull(uri)?.let { objectMapper.readValue(it) }
