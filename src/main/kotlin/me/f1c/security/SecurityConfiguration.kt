package me.f1c.security

import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import me.f1c.domain.token.TokenClientService
import me.f1c.security.ClientCredentialsGenerator.BCRYPT_PASSWORD_ENCODER_STRENGTH
import me.f1c.util.CryptoUtil.generateECKeyPair
import me.f1c.util.CryptoUtil.toECKey
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationService
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings
import org.springframework.security.oauth2.server.authorization.token.JwtGenerator
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.intercept.AuthorizationFilter
import java.time.Duration
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Configuration
@EnableWebSecurity
class SecurityConfiguration {
    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    @Throws(Exception::class)
    fun defaultSecurityFilterChain(
        http: HttpSecurity,
        authorizationServerSettings: AuthorizationServerSettings,
        authorizationService: OAuth2AuthorizationService,
        registeredClientRepository: RegisteredClientRepository,
        jwtEncoder: JwtEncoder,
        jwtDecoder: JwtDecoder,
        jwkSource: JWKSource<SecurityContext>,
    ): SecurityFilterChain {
        val authorizationServerConfigurer = OAuth2AuthorizationServerConfigurer.authorizationServer()

        return http
            .with(authorizationServerConfigurer) { authorizationServer ->
                authorizationServer
                    .registeredClientRepository(registeredClientRepository)
                    .authorizationService(authorizationService)
                    .tokenGenerator(JwtGenerator(jwtEncoder))
                    .authorizationServerSettings(authorizationServerSettings)
            }.authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers("/admin/**")
                    .hasRole(TokenRole.ADMIN.name)
                    .anyRequest()
                    .permitAll()
            }.sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }.addFilterBefore(JwtAuthenticationFilter(jwkSource), AuthorizationFilter::class.java)
            .exceptionHandling {
                it
                    .authenticationEntryPoint { _, response, authException ->
                        authException.printStackTrace()
                        response.sendError(401, authException.message)
                    }
            }.build()
    }

    @Bean
    fun registeredClientRepository(f1CSecurityProperties: F1CSecurityProperties): RegisteredClientRepository {
        val registeredClient =
            RegisteredClient
                .withId(Uuid.random().toString())
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .authorizationGrantType(AuthorizationGrantType.JWT_BEARER)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofHours(1)).build())
                .clientId(f1CSecurityProperties.clientId)
                .clientSecret(f1CSecurityProperties.clientSecret)
                .scope("ROLE_ADMIN")
                .build()
        return InMemoryRegisteredClientRepository(registeredClient)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder(BCRYPT_PASSWORD_ENCODER_STRENGTH)

    @Bean
    fun authorizationService(): OAuth2AuthorizationService = InMemoryOAuth2AuthorizationService()

    @Bean
    fun jwkSource(): JWKSource<SecurityContext> {
        val ecKey = generateECKeyPair().toECKey()
        val jwkSet = JWKSet(ecKey)
        return ImmutableJWKSet(jwkSet)
    }

    @Bean
    fun jwtDecoder(jwkSource: JWKSource<SecurityContext>): JwtDecoder = JwtDecoderImpl(jwkSource)

    @Bean
    fun jwtEncoder(
        jwkSource: JWKSource<SecurityContext>,
        tokenClientService: TokenClientService,
    ): JwtEncoder = JwtEncoderImpl(jwkSource, tokenClientService)

    @Bean
    fun authorizationServerSettings(): AuthorizationServerSettings = AuthorizationServerSettings.builder().build()
}
