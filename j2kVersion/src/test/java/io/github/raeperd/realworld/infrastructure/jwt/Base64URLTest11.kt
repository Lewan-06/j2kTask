package io.github.raeperd.realworld.infrastructure.jwt

import org.junit.jupiter.api.Test

internal class Base64URLTest {
    @Test
    fun when_encode_return_expected_string() {
        assertThat(base64URLFromString(RAW_STRING)).isEqualTo(ENCODED_STRING)
    }

    @Test
    fun when_decode_return_expected_string() {
        assertThat(stringFromBase64URL(ENCODED_STRING)).isEqualTo(RAW_STRING)
    }

    @Test
    fun when_encode_and_then_decode_expect_same() {
        val rawString: String = generateRandomString()

        val encodedString: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            base64URLFromString(rawString)
        assertThat(stringFromBase64URL(encodedString)).isEqualTo(rawString)
    }

    private fun generateRandomString(): String {
        val bytes: ByteArray = ByteArray(7)
        Random().nextBytes(bytes)
        return String(bytes)
    }

    companion object {
        private val RAW_STRING: String = "something"
        private val ENCODED_STRING: String = "c29tZXRoaW5n"
    }
}