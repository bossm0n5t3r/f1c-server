package me.f1c.security

import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtDecoder

class JwtDecoderImpl(
    private val jwkSource: JWKSource<SecurityContext>,
) : JwtDecoder {
    override fun decode(token: String): Jwt {
        val ecKey =
            (jwkSource as ImmutableJWKSet)
                .jwkSet
                .keys
                .first()
                .toECKey()
        return JwtProvider.decodeJWE(token, ecKey)
    }
}
