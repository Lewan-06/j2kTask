package io.github.raeperd.realworld.domain.jwt

interface JWTDeserializer {
    fun jwtPayloadFromJWT(jwtToken: String?): JWTPayload?
}
