package io.github.raeperd.realworld.infrastructure.jwt

import io.github.raeperd.realworld.domain.user.User

@ExtendWith(MockitoExtension::class)
internal class UserJWTPayloadTest {
    @Test
    fun when_getUserId_expect_return_user_id(@Mock user: User) {
        `when`(user.getId()).thenReturn(1L)
        val jwtPayload: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            UserJWTPayload.of(user, now().getEpochSecond())

        assertThat(jwtPayload.getUserId()).isOne()
    }

    @Test
    fun when_expired_expect_isExpired_return_true(@Mock user: User?) {
        val jwtPayload: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            UserJWTPayload.of(user, MIN.getEpochSecond())

        assertThat(jwtPayload.isExpired()).isTrue()
    }

    @Test
    fun when_not_expired_expect_isExpired_return_false(@Mock user: User?) {
        val jwtPayload: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            UserJWTPayload.of(user, MAX.getEpochSecond())

        assertThat(jwtPayload.isExpired()).isFalse()
    }

    @Test
    fun when_to_string_expect_return_shortest_json_string() {
        val sampleUser: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            userWithIdAndEmail(2L, "user@email.com")
        val jwtPayload: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            UserJWTPayload.of(sampleUser, MAX.getEpochSecond())

        assertThat(jwtPayload)
            .hasToString(
                format(
                    "{\"sub\":%d,\"name\":\"%s\",\"iat\":%d}",
                    2L, "user@email.com", MAX.getEpochSecond()
                )
            )
    }
}