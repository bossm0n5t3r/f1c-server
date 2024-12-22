package me.f1c.security

import me.f1c.security.ClientCredentialsGenerator.toBCrypt
import org.junit.jupiter.api.Test
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
class ClientCredentialsGeneratorTest {
    @Test
    fun generateClientIdAndBCryptPasswordEncodedPassword() {
        val clientId = ClientCredentialsGenerator.generateClientId()
        val clientSecret = ClientCredentialsGenerator.generateSecureRandomString(32)
        val encodedClientSecret = clientSecret.toBCrypt()
        println("clientId: $clientId")
        println("clientSecret: $clientSecret")
        println("Base64 encoded clientId:clientSecret: ${Base64.encode("$clientId:$clientSecret".toByteArray())}")
        println("encodedClientSecret: $encodedClientSecret")
    }
}
