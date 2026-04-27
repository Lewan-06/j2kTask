package io.github.raeperd.realworld.domain.user

import org.junit.jupiter.api.Test

@ExtendWith(MockitoExtension::class)
internal class PasswordTest {
    @Mock
    private val passwordEncoder: PasswordEncoder? = null

    @Test
    fun when_create_password_expect_passwordEncoder_encode() {
        Password.of("raw-password", passwordEncoder)

        verify(passwordEncoder, times(1)).encode("raw-password")
    }

    @Test
    fun when_matches_password_expect_passwordEncoder_matches() {
        given(passwordEncoder.encode("raw-password")).willReturn("encoded-password")

        val password: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            Password.of("raw-password", passwordEncoder)
        password.matchesPassword("raw-password", passwordEncoder)

        then(passwordEncoder).should(times(1)).matches("raw-password", "encoded-password")
    }
}