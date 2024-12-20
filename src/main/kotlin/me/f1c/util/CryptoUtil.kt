package me.f1c.util

import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.spec.AlgorithmParameterSpec
import java.security.spec.ECGenParameterSpec
import java.security.spec.NamedParameterSpec
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
object CryptoUtil {
    private const val ALGORITHM_EC = "EC"
    private const val ALGORITHM_ED25519 = "Ed25519"
    private const val CURVE_P_521 = "secp521r1"

    private fun generateKeyPair(
        algorithm: String,
        algorithmParameterSpec: AlgorithmParameterSpec,
    ): KeyPair {
        val keyPairGenerator = KeyPairGenerator.getInstance(algorithm)
        keyPairGenerator.initialize(algorithmParameterSpec)
        return keyPairGenerator.generateKeyPair()
    }

    fun generateECKeyPair(): KeyPair = generateKeyPair(ALGORITHM_EC, ECGenParameterSpec(CURVE_P_521))

    private fun generateED25519KeyPair(): KeyPair = generateKeyPair(ALGORITHM_ED25519, NamedParameterSpec.ED25519)

    fun generateEncodedED25519KeyPair(): Pair<String, String> {
        val keyPair = generateED25519KeyPair()
        return Base64.encode(keyPair.public.encoded) to Base64.encode(keyPair.private.encoded)
    }
}
