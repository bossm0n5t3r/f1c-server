package me.f1c.security

import me.f1c.util.CryptoUtil.toECKey
import me.f1c.util.CryptoUtil.toKeyPair
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("f1c-client.security")
data class F1CSecurityProperties(
    val clientId: String,
    val clientSecret: String,
) {
    fun toECKey() = (clientId to clientSecret).toKeyPair().toECKey()
}
