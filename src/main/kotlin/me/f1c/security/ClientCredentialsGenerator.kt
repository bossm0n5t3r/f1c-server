package me.f1c.security

import me.f1c.util.StringUtil.ALPHA_NUMERIC_CHARS
import me.f1c.util.StringUtil.SPECIAL_CHARS
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.security.SecureRandom
import kotlin.streams.asSequence
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
object ClientCredentialsGenerator {
    const val BCRYPT_PASSWORD_ENCODER_STRENGTH = 12

    private val bCryptPasswordEncoder = BCryptPasswordEncoder(BCRYPT_PASSWORD_ENCODER_STRENGTH)

    fun generateClientId(): String = Uuid.random().toHexString()

    fun generateSecureRandomString(length: Int): String {
        val allowedChars = ALPHA_NUMERIC_CHARS + SPECIAL_CHARS
        return SecureRandom()
            .ints(length.toLong(), 0, allowedChars.size)
            .asSequence()
            .map(allowedChars::get)
            .joinToString("")
    }

    fun String.toBCrypt(): String = bCryptPasswordEncoder.encode(this)
}
