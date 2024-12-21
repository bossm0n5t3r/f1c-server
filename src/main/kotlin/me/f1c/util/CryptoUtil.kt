package me.f1c.util

import com.nimbusds.jose.jwk.Curve
import com.nimbusds.jose.jwk.ECKey
import java.security.KeyFactory
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.security.interfaces.ECPublicKey
import java.security.spec.AlgorithmParameterSpec
import java.security.spec.ECGenParameterSpec
import java.security.spec.NamedParameterSpec
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalEncodingApi::class, ExperimentalUuidApi::class)
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

    fun generateEncodedECKeyPair(): Pair<String, String> {
        val keyPair = generateECKeyPair()
        return Base64.encode(keyPair.public.encoded) to Base64.encode(keyPair.private.encoded)
    }

    fun generateEncodedED25519KeyPair(): Pair<String, String> {
        val keyPair = generateED25519KeyPair()
        return Base64.encode(keyPair.public.encoded) to Base64.encode(keyPair.private.encoded)
    }

    fun Pair<String, String>.toKeyPair(): KeyPair {
        val publicKey = this.first.toPublicKey(ALGORITHM_EC)
        val privateKey = this.second.toPrivateKey(ALGORITHM_EC)
        return KeyPair(publicKey, privateKey)
    }

    fun KeyPair.toECKey(): ECKey =
        ECKey
            .Builder(Curve.P_521, this.public as ECPublicKey)
            .privateKey(this.private)
            .keyID(Uuid.random().toString())
            .build()

    private fun String.toPublicKey(algorithm: String): PublicKey {
        val keyBytes = Base64.decode(this)
        val keySpec = X509EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance(algorithm)
        return keyFactory.generatePublic(keySpec)
    }

    private fun String.toPrivateKey(algorithm: String): PrivateKey {
        val keyBytes = Base64.decode(this)
        val keySpec = PKCS8EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance(algorithm)
        return keyFactory.generatePrivate(keySpec)
    }
}
