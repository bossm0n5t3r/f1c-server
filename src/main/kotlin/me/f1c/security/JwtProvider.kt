package me.f1c.security

import com.nimbusds.jose.EncryptionMethod
import com.nimbusds.jose.JWEAlgorithm
import com.nimbusds.jose.JWEHeader
import com.nimbusds.jose.JWEObject
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.JWSObject
import com.nimbusds.jose.JWSSigner
import com.nimbusds.jose.Payload
import com.nimbusds.jose.crypto.ECDHDecrypter
import com.nimbusds.jose.crypto.ECDHEncrypter
import com.nimbusds.jose.crypto.ECDSASigner
import com.nimbusds.jose.crypto.ECDSAVerifier
import com.nimbusds.jose.jwk.ECKey
import com.nimbusds.jwt.EncryptedJWT
import me.f1c.util.ObjectMapperUtil
import org.springframework.security.oauth2.jwt.Jwt

object JwtProvider {
    private const val ISSUER = "f1c"
    private val DEFAULT_CLAIMS = mapOf("iss" to ISSUER)
    const val ROLE = "role"

    fun createJWS(
        ecKey: ECKey,
        headers: Map<String, Any> = emptyMap(),
        claims: Map<String, Any> = DEFAULT_CLAIMS,
    ): String {
        val signer: JWSSigner = ECDSASigner(ecKey)

        val jwsObject =
            JWSObject(
                JWSHeader
                    .Builder(JWSAlgorithm.ES512)
                    .keyID(ecKey.keyID)
                    .apply { headers.forEach { (key, value) -> customParam(key, value) } }
                    .build(),
                Payload(ObjectMapperUtil.objectMapper.writeValueAsString(DEFAULT_CLAIMS + claims)),
            )

        jwsObject.sign(signer)

        return jwsObject.serialize()
    }

    fun verifyJWS(
        token: String,
        ecKey: ECKey,
    ): Boolean =
        try {
            val jwsObject = JWSObject.parse(token)
            jwsObject.verify(ECDSAVerifier(ecKey.toPublicJWK()))
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }

    fun createJWE(
        ecKey: ECKey,
        headers: Map<String, Any> = emptyMap(),
        claims: Map<String, Any> = DEFAULT_CLAIMS,
    ): String {
        val encryptor = ECDHEncrypter(ecKey.toPublicJWK())

        val jweObject =
            JWEObject(
                JWEHeader
                    .Builder(JWEAlgorithm.ECDH_ES_A256KW, EncryptionMethod.A256GCM)
                    .keyID(ecKey.keyID)
                    .apply {
                        headers
                            .filterKeys { JWEHeader.getRegisteredParameterNames().contains(it).not() }
                            .forEach { (key, value) -> customParam(key, value) }
                    }.build(),
                Payload(ObjectMapperUtil.objectMapper.writeValueAsString(DEFAULT_CLAIMS + claims)),
            )

        jweObject.encrypt(encryptor)

        return jweObject.serialize()
    }

    fun verifyJWE(
        jwe: String,
        ecKey: ECKey,
    ): Boolean =
        try {
            val encryptedJWT = EncryptedJWT.parse(jwe)
            encryptedJWT.decrypt(ECDHDecrypter(ecKey))
            true
        } catch (e: Exception) {
            false
        }

    fun verifyRoleFromJWT(
        token: String,
        ecKey: ECKey,
        role: UserRole,
    ): Boolean =
        try {
            val jwsObject =
                EncryptedJWT
                    .parse(token)
                    .apply { decrypt(ECDHDecrypter(ecKey)) }
            val claims = jwsObject.payload.toJSONObject()
            claims[ROLE] == role.role
        } catch (e: Exception) {
            false
        }

    fun decodeJWE(
        token: String,
        ecKey: ECKey,
    ): Jwt {
        val jweObject =
            EncryptedJWT.parse(token).apply {
                decrypt(ECDHDecrypter(ecKey))
            }
        return Jwt(
            token,
            jweObject.jwtClaimsSet.issueTime.toInstant(),
            jweObject.jwtClaimsSet.expirationTime.toInstant(),
            jweObject.header.toJSONObject(),
            jweObject.jwtClaimsSet.claims,
        )
    }
}
