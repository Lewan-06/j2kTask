package io.github.raeperd.realworld.domain.user

import org.springframework.security.crypto.password.PasswordEncoder

@Embeddable
internal class Password {
    @Column(name = "password", nullable = false)
    private var encodedPassword: String? = null

    private constructor(encodedPassword: String?) {
        this.encodedPassword = encodedPassword
    }

    protected constructor()

    fun matchesPassword(rawPassword: String?, passwordEncoder: PasswordEncoder): Boolean {
        return passwordEncoder.matches(rawPassword, encodedPassword)
    }

    companion object {
        fun of(rawPassword: String?, passwordEncoder: PasswordEncoder): Password {
            return Password(passwordEncoder.encode(rawPassword))
        }
    }
}
