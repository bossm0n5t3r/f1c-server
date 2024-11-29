package me.f1c.port.external

import org.springframework.web.client.RestClient

interface ExternalF1Client {
    val restClient: RestClient

    fun <T> callGet(
        uri: String,
        clazz: Class<T>,
    ): T?
}
