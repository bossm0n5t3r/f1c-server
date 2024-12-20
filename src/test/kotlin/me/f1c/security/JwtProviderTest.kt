package me.f1c.security

import me.f1c.security.JwtProvider.toECKey
import me.f1c.util.CryptoUtil
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.Test
import kotlin.test.assertTrue

class JwtProviderTest {
    @Test
    fun createJWSTest() {
        assertDoesNotThrow {
            val ecKey = CryptoUtil.generateECKeyPair().toECKey()
            val token = JwtProvider.createJWS(ecKey)
            println(token)
        }
    }

    @Test
    fun verifyJWSTest() {
        val ecKey = CryptoUtil.generateECKeyPair().toECKey()
        val token = JwtProvider.createJWS(ecKey)

        assertTrue(JwtProvider.verifyJWS(token, ecKey))
    }

    @Test
    fun createJWETest() {
        assertDoesNotThrow {
            val ecKey = CryptoUtil.generateECKeyPair().toECKey()
            val token = JwtProvider.createJWE(ecKey)
            println(token)
        }
    }

    @Test
    fun verifyJWETest() {
        val ecKey = CryptoUtil.generateECKeyPair().toECKey()
        val token = JwtProvider.createJWE(ecKey)

        assertTrue(JwtProvider.verifyJWE(token, ecKey))
    }
}
