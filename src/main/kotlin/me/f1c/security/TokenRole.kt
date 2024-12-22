package me.f1c.security

import org.springframework.security.core.GrantedAuthority

enum class TokenRole(
    val role: String,
) : GrantedAuthority {
    UNAUTHORIZED("ROLE_UNAUTHORIZED"), // 미인증
    TEMPORARY("ROLE_TEMPORARY"), // 임시
    MEMBER("ROLE_MEMBER"), // 회원
    ADMIN("ROLE_ADMIN"), // 관리자
    WITHDRAWAL("ROLE_WITHDRAWAL"), // 탈퇴
    ;

    override fun getAuthority(): String = this.role

    companion object {
        fun String.toTokenRole(): TokenRole? = entries.find { it.role == this }
    }
}
