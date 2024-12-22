package me.f1c.security

import me.f1c.security.JwtProvider.ROLE
import me.f1c.util.CryptoUtil
import me.f1c.util.CryptoUtil.generateEncodedECKeyPair
import me.f1c.util.CryptoUtil.toECKey
import me.f1c.util.CryptoUtil.toKeyPair
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.Test
import kotlin.test.assertTrue

class JwtProviderTest {
    @Test
    fun createJWSTest() {
        assertDoesNotThrow {
            val ecKey = CryptoUtil.generateECKeyPair().toECKey()
            val token = JwtProvider.createJWS(ecKey, emptyMap(), mapOf(ROLE to UserRole.entries.random().role))
            println(token)
        }
    }

    @Test
    fun verifyJWSTest() {
        val ecKey = CryptoUtil.generateECKeyPair().toECKey()
        val randomUserRole = UserRole.entries.random()
        val token = JwtProvider.createJWS(ecKey, emptyMap(), mapOf(ROLE to randomUserRole.role))

        assertTrue(JwtProvider.verifyJWS(token, ecKey))
    }

    @Test
    fun createJWETest() {
        assertDoesNotThrow {
            val ecKey = CryptoUtil.generateECKeyPair().toECKey()
            val token = JwtProvider.createJWE(ecKey, emptyMap(), mapOf(ROLE to UserRole.entries.random().role))
            println(token)
        }
    }

    @Test
    fun verifyJWETest() {
        val ecKey = CryptoUtil.generateECKeyPair().toECKey()
        val randomUserRole = UserRole.entries.random()
        val token = JwtProvider.createJWE(ecKey, emptyMap(), mapOf(ROLE to randomUserRole.role))

        assertTrue(JwtProvider.verifyJWE(token, ecKey))
    }

    @Test
    fun createAndVerifyJWTUsingGenerateEncodedECKeyPairTest() {
        val encodedECKeyPair = generateEncodedECKeyPair()
        val ecKey = encodedECKeyPair.toKeyPair().toECKey()
        val randomUserRole = UserRole.entries.random()
        val token =
            assertDoesNotThrow {
                JwtProvider.createJWE(ecKey, emptyMap(), mapOf(ROLE to randomUserRole.role))
            }

        assertTrue(JwtProvider.verifyJWE(token, ecKey))

        assertTrue(JwtProvider.verifyRoleFromJWT(token, ecKey, randomUserRole))
    }
}
