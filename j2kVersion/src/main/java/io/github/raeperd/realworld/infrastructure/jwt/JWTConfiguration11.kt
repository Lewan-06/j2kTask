package io.github.raeperd.realworld.infrastructure.jwt

import com.fasterxml.jackson.databind.ObjectMapper

@Configuration
internal class JWTConfiguration {
    @Bean
    fun hmacSHA256JWTService(objectMapper: ObjectMapper?): HmacSHA256JWTService? {
        return HmacSHA256JWTService(SECRET, JWT_DURATION_SECONDS, objectMapper)
    }

    companion object {
        private val SECRET: ByteArray? = "SOME_SIGNATURE_SECRET".getBytes(StandardCharsets.UTF_8)
        private val JWT_DURATION_SECONDS: Int = 2 * 60 * 60
    }
}
