package me.f1c.security

import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.Test
import kotlin.test.assertTrue

class JwtProviderTest {
    @Test
    fun generateEncodedED25519KeyPairTest() {
        assertDoesNotThrow {
            JwtProvider.generateEncodedED25519KeyPair()
        }
    }

    @Test
    fun createJWSTest() {
        assertDoesNotThrow {
            val (_, encodedPrivateKey) = JwtProvider.generateEncodedED25519KeyPair()
            val token = JwtProvider.createJWS(encodedPrivateKey)
            println(token)
        }
    }

    @Test
    fun verifyJWSTest() {
        val (encodedPublicKey, encodedPrivateKey) = JwtProvider.generateEncodedED25519KeyPair()
        val token = JwtProvider.createJWS(encodedPrivateKey)

        assertTrue(JwtProvider.verifyJWS(token, encodedPublicKey))
    }

    @Test
    fun createJWETest() {
        assertDoesNotThrow {
            val (encodedPublicKey, _) = JwtProvider.generateEncodedECKeyPair()
            val token = JwtProvider.createJWE(encodedPublicKey)
            println(token)
        }
    }

    @Test
    fun verifyJWETest() {
        val (encodedPublicKey, encodedPrivateKey) = JwtProvider.generateEncodedECKeyPair()
        val token = JwtProvider.createJWE(encodedPublicKey)

        assertTrue(JwtProvider.verifyJWE(token, encodedPrivateKey))
    }
}
