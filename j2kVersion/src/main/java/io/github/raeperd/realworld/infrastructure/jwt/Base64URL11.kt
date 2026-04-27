package io.github.raeperd.realworld.infrastructure.jwt

import java.nio.charset.StandardCharsets

internal object Base64URL {
    fun base64URLFromString(rawString: String): String? {
        return base64URLFromBytes(rawString.getBytes(StandardCharsets.UTF_8))
    }

    fun base64URLFromBytes(bytes: ByteArray?): String? {
        return Base64.getUrlEncoder().withoutPadding()
            .encodeToString(bytes)
    }

    fun stringFromBase64URL(base64URL: String?): String? {
        return String(Base64.getUrlDecoder().decode(base64URL))
    }
}
