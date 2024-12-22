package me.f1c.security

import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import me.f1c.security.JwtProvider.ROLE
import me.f1c.security.TokenRole.Companion.toTokenRole
import me.f1c.util.Constants.BEARER_
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(
    private val jwkSource: JWKSource<SecurityContext>,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (authHeader != null && authHeader.startsWith(BEARER_)) {
            val jwtToken = authHeader.substringAfter(BEARER_)
            val ecKey =
                (jwkSource as ImmutableJWKSet)
                    .jwkSet
                    .keys
                    .first()
                    .toECKey()

            if (JwtProvider.verifyJWE(jwtToken, ecKey)) {
                val jwt = JwtProvider.decodeJWE(jwtToken, ecKey)
                val userRole = (jwt.claims[ROLE] as? String)?.toTokenRole() ?: TokenRole.UNAUTHORIZED
                val authToken =
                    JwtAuthenticationToken(
                        jwt,
                        listOf(userRole),
                    )
                SecurityContextHolder.getContext().authentication = authToken
            }
        }
        filterChain.doFilter(request, response)
    }
}
