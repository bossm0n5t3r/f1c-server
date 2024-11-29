package me.f1c.adapter.external

import me.f1c.configuration.LOGGER
import me.f1c.port.external.ExternalF1Client
import me.f1c.util.Constants.CONFLICT_ARROW
import me.f1c.util.Constants.REQUEST_ARROW
import me.f1c.util.Constants.RESPONSE_ARROW
import me.f1c.util.ObjectMapperUtil.objectMapper
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.client.RestClient

/**
 * https://github.com/jolpica/jolpica-f1
 */
@Component
class JolpicaF1ClientImpl(
    override val restClient: RestClient,
) : ExternalF1Client {
    companion object {
        private const val JOLPICA_F1_API = "https://api.jolpi.ca/ergast/f1"
    }

    fun getRaceApi(season: Int): String = "$JOLPICA_F1_API/$season/races/?format=json"

    fun getResultApi(
        season: Int,
        round: Int,
    ) = "${JOLPICA_F1_API}/$season/$round/results/?format=json"

    fun getLastResultApi(season: Int) = "${JOLPICA_F1_API}/$season/last/results/?format=json"

    override fun <T> callGet(
        uri: String,
        clazz: Class<T>,
    ): T? {
        val clazzSimpleName = clazz.simpleName
        return try {
            LOGGER.info("[{} {}] callGet: {}", REQUEST_ARROW, uri, clazzSimpleName)
            val rawResponse =
                restClient
                    .get()
                    .uri(uri)
                    .retrieve()
                    .toEntity(String::class.java)
            LOGGER.info("[{} {}] callGet: {}, {}", RESPONSE_ARROW, uri, clazzSimpleName, rawResponse)
            val bodyString = requireNotNull(rawResponse.body)
            objectMapper.readValue(bodyString, clazz)
        } catch (e: HttpClientErrorException) {
            LOGGER.error("[{} {}] callGet: {}, {}, ", RESPONSE_ARROW, uri, clazzSimpleName, e.message, e)
            throw e
        } catch (e: HttpServerErrorException) {
            LOGGER.warn("[{} {}] callGet: {}, {}, ", RESPONSE_ARROW, uri, clazzSimpleName, e.message, e)
            null
        } catch (e: Exception) {
            LOGGER.error("[{} {}] callGet: {}, {}, ", CONFLICT_ARROW, uri, clazzSimpleName, e.message, e)
            throw e
        }
    }
}
