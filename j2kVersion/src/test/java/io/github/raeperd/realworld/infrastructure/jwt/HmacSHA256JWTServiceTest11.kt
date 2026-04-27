package io.github.raeperd.realworld.infrastructure.jwt

import com.fasterxml.jackson.databind.ObjectMapper

@ExtendWith(MockitoExtension::class)
@JsonTest
internal class HmacSHA256JWTServiceTest {
    private var jwtService: HmacSHA256JWTService? = null

    @Autowired
    private val objectMapper: ObjectMapper? = null

    @BeforeEach
    fun initializeService() {
        objectMapper.registerModule(ParameterNamesModule(PROPERTIES))
        this.jwtService = HmacSHA256JWTService(SECRET, 3, objectMapper)
    }

    @Test
    fun when_generateToken_expect_result_startsWith_encodedHeader() {
        val user: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            userWithIdAndEmail(1L, "user@email.com")

        val token: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            jwtService.jwtFromUser(user)

        assertThat(token).startsWith(base64URLFromString("{\"alg\":\"HS256\",\"type\":\"JWT\"}"))
    }

    @Test
    fun when_JWTPayloadFromString_with_malformed_token_expect_IllegalArgumentException() {
        assertThatThrownBy({ jwtService.jwtPayloadFromJWT("MALFORMED_TOKEN-2-_without_dot") }
        ).isInstanceOf(IllegalArgumentException::class.java).hasMessageStartingWith("Malformed JWT:")
    }

    @Test
    fun when_JWTPayloadFromString_with_not_starts_with_header_expect_IllegalArgumentException() {
        assertThatThrownBy({ jwtService.jwtPayloadFromJWT("HEADER_bas64-._base64-payload.SIGN") }
        ).isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageStartingWith("Malformed JWT! Token must starts with header")
    }

    @Test
    fun when_JWTPayloadFromString_with_invalid_sign_expect_IllegalArgumentException() {
        assertThatThrownBy({ jwtService.jwtPayloadFromJWT(JWT_HEADER_EXPECTED.toString() + "._base64-payload.INVALID_1_SIGN") }
        ).isInstanceOf(IllegalArgumentException::class.java).hasMessageStartingWith("Token has invalid signature")
    }

    @Test
    fun when_JWTPayloadFromString_with_invalid_payload_expect_IllegalArgumentException() {
        val invalidPayloadToken: String? = invalidPayloadToken()

        assertThatThrownBy({ jwtService.jwtPayloadFromJWT(invalidPayloadToken) }
        ).isInstanceOf(IllegalArgumentException::class.java).hasMessageStartingWith("Malformed JWT")
    }

    @Test
    fun when_JWTPayloadFromString_token_has_expired_expect_InvalidJWTException() {
        val expiredToken: String? = expiredToken()

        assertThatThrownBy({ jwtService.jwtPayloadFromJWT(expiredToken) }
        ).isInstanceOf(IllegalArgumentException::class.java).hasMessageContaining("Token expired")
    }

    @Test
    fun when_JWTPayloadFromString_with_valid_token_expect_return_valid() {
        val user: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            userWithIdAndEmail(1L, "user@email.com")

        val token: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            jwtService.jwtFromUser(user)
        val payloadFromToken: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            jwtService.jwtPayloadFromJWT(token)

        assertThat(payloadFromToken)
            .matches({ payload -> !payload.isExpired() })
            .matches({ payload -> payload.getUserId() === 1L })
            .matches({ payload ->
                valueOf(payload).startsWith(
                    format(
                        "{\"sub\":%d,\"name\":\"%s\",",
                        1L,
                        user.getEmail()
                    )
                )
            })
    }

    private fun invalidPayloadToken(): String? {
        val message: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            format("%s.%s", JWT_HEADER_EXPECTED, "INVALID_PAYLOAD")
        return base64URLFromBytes(HmacSHA256.sign(SECRET, message))
    }

    private fun expiredToken(): String? {
        val user: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            userWithIdAndEmail(1L, "user@email.com")
        return HmacSHA256JWTService(SECRET, -1, ObjectMapper())
            .jwtFromUser(user)
    }

    companion object {
        private val JWT_HEADER_EXPECTED: String? = base64URLFromString("{\"alg\":\"HS256\",\"type\":\"JWT\"}")
        private val SECRET: ByteArray? = "SOME_SECRET".getBytes(StandardCharsets.UTF_8)
    }
}