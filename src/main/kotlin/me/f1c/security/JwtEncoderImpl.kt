package me.f1c.security

import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import me.f1c.domain.token.TokenClientService
import me.f1c.security.JwtProvider.ROLE
import me.f1c.security.JwtProvider.SUB
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters

class JwtEncoderImpl(
    private val jwkSource: JWKSource<SecurityContext>,
    private val tokenClientService: TokenClientService,
) : JwtEncoder {
    override fun encode(parameters: JwtEncoderParameters): Jwt {
        val ecKey =
            (jwkSource as ImmutableJWKSet)
                .jwkSet
                .keys
                .first()
                .toECKey()
        val headers = parameters.jwsHeader?.headers ?: emptyMap()
        val clientId = parameters.claims.claims[SUB] as? String ?: ""
        val tokenRole = tokenClientService.findByClientId(clientId)?.role ?: TokenRole.UNAUTHORIZED
        val claims = parameters.claims.claims + mapOf(ROLE to tokenRole.role)
        val token =
            JwtProvider.createJWE(
                ecKey,
                headers,
                claims,
            )
        return Jwt
            .withTokenValue(token)
            .headers { headers.forEach { (key, value) -> it[key] = value } }
            .claims { claims.forEach { (key, value) -> it[key] = value } }
            .build()
    }
}
