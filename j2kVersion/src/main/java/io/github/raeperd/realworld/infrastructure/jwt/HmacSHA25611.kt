package io.github.raeperd.realworld.infrastructure.jwt

import javax.crypto.Mac

internal object HmacSHA256 {
    private val HMAC_SHA256_ALGORITHM: String = "HmacSHA256"

    fun sign(secret: ByteArray?, message: String): ByteArray? {
        try {
            val hmacSHA256: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
                Mac.getInstance(
                    HMAC_SHA256_ALGORITHM
                )
            hmacSHA256.init(SecretKeySpec(secret, HMAC_SHA256_ALGORITHM))
            return hmacSHA256.doFinal(message.getBytes(StandardCharsets.UTF_8))
        } catch (exception: Exception) {
            throw HmacSHA256SignFailedException(exception)
        }
    }

    private class HmacSHA256SignFailedException(cause: Throwable?) : RuntimeException(cause)
}
