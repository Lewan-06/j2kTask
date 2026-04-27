package io.github.raeperd.realworld.infrastructure.jwt

import com.fasterxml.jackson.databind.ObjectMapper

internal class HmacSHA256JWTService(
    private val secret: ByteArray?,
    private val durationSeconds: Long,
    objectMapper: ObjectMapper?
) :
    JWTSerializer, JWTDeserializer {
    private val objectMapper: ObjectMapper?

    init {
        this.objectMapper = objectMapper
    }

    @Override
    fun jwtFromUser(user: User?): String? {
        val messageToSign: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            JWT_HEADER.concat(".").concat(jwtPayloadFromUser(user))
        val signature: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            HmacSHA256.sign(
                secret, messageToSign
            )
        return messageToSign.concat(".").concat(base64URLFromBytes(signature))
    }

    private fun jwtPayloadFromUser(user: User?): String? {
        val jwtPayload: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            UserJWTPayload.of(user, now().getEpochSecond() + durationSeconds)
        return base64URLFromString(jwtPayload.toString())
    }

    @Override
    fun jwtPayloadFromJWT(jwtToken: String): JWTPayload? {
        kotlin.require(JWT_PATTERN.matcher(jwtToken).matches()) { "Malformed JWT: " + jwtToken }

        val splintedTokens: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            jwtToken.split("\\.")
        kotlin.require(
            splintedTokens.get(0).equals(JWT_HEADER)
        ) { "Malformed JWT! Token must starts with header: " + JWT_HEADER }

        val signatureBytes: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            HmacSHA256.sign(
                secret, splintedTokens.get(0).concat(".").concat(splintedTokens.get(1))
            )
        kotlin.require(base64URLFromBytes(signatureBytes).equals(splintedTokens.get(2))) { "Token has invalid signature: " + jwtToken }

        try {
            val decodedPayload: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
                stringFromBase64URL(splintedTokens.get(1))
            val jwtPayload: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
                objectMapper.readValue(
                    decodedPayload,
                    UserJWTPayload::class.java
                )
            kotlin.require(!jwtPayload.isExpired()) { "Token expired" }
            return jwtPayload
        } catch (exception: Exception) {
            throw IllegalArgumentException(exception)
        }
    }

    companion object {
        private val JWT_HEADER: String? = base64URLFromString("{\"alg\":\"HS256\",\"type\":\"JWT\"}")
        private val BASE64URL_PATTERN: String = "[\\w_\\-]+"
        private val JWT_PATTERN: Pattern? = compile(
            format(
                "^(%s\\.)(%s\\.)(%s)$",
                BASE64URL_PATTERN, BASE64URL_PATTERN, BASE64URL_PATTERN
            )
        )
    }
}
