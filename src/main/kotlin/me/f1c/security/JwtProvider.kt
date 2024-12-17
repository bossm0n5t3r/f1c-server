package me.f1c.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Jwks
import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.NamedParameterSpec
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalEncodingApi::class, ExperimentalUuidApi::class)
object JwtProvider {
    private const val ALGORITHM_EC = "EC"

    fun generateEncodedED25519KeyPair(): Pair<String, String> {
        val keyPairGenerator = KeyPairGenerator.getInstance(Jwks.CRV.Ed25519.id)
        keyPairGenerator.initialize(NamedParameterSpec.ED25519)
        val keyPair = keyPairGenerator.generateKeyPair()
        return Base64.encode(keyPair.public.encoded) to Base64.encode(keyPair.private.encoded)
    }

    private fun String.toPublicKey(algorithm: String = Jwks.CRV.Ed25519.id): PublicKey {
        val keyBytes = Base64.decode(this)
        val keySpec = X509EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance(algorithm)
        return keyFactory.generatePublic(keySpec)
    }

    private fun String.toPrivateKey(algorithm: String = Jwks.CRV.Ed25519.id): PrivateKey {
        val keyBytes = Base64.decode(this)
        val keySpec = PKCS8EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance(algorithm)
        return keyFactory.generatePrivate(keySpec)
    }

    fun createJWS(
        encodedPrivateKey: String,
        issuer: String = Uuid.random().toHexString(),
        claim: Map<String, String> = emptyMap(),
    ): String =
        Jwts
            .builder()
            .issuer(issuer)
            .claims(claim)
            .signWith(encodedPrivateKey.toPrivateKey(), Jwts.SIG.EdDSA)
            .compact()

    fun verifyJWS(
        token: String,
        encodedPublicKey: String,
    ): Boolean =
        try {
            Jwts
                .parser()
                .verifyWith(encodedPublicKey.toPublicKey())
                .build()
                .parseSignedClaims(token)
                .also { println(it) }
            true
        } catch (e: Exception) {
            false
        }

    fun generateEncodedECKeyPair(): Pair<String, String> {
        val keyPair =
            Jwts.SIG.ES512
                .keyPair()
                .build()
        return Base64.encode(keyPair.public.encoded) to Base64.encode(keyPair.private.encoded)
    }

    fun createJWE(
        encodedPublicKey: String,
        issuer: String = Uuid.random().toHexString(),
        claims: Map<String, String> = emptyMap(),
    ): String =
        Jwts
            .builder()
            .issuer(issuer)
            .claims(claims)
            .encryptWith(encodedPublicKey.toPublicKey(ALGORITHM_EC), Jwts.KEY.ECDH_ES_A256KW, Jwts.ENC.A256GCM)
            .compact()

    fun verifyJWE(
        jwe: String,
        encodedPrivateKey: String,
    ): Boolean =
        try {
            Jwts
                .parser()
                .decryptWith(encodedPrivateKey.toPrivateKey(ALGORITHM_EC))
                .build()
                .parseEncryptedClaims(jwe)
                .also { println(it) }
            true
        } catch (e: Exception) {
            false
        }
}
