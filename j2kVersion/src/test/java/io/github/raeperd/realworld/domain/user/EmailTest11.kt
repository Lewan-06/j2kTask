package io.github.raeperd.realworld.domain.user

import org.junit.jupiter.api.Test

internal class EmailTest {
    @Test
    fun when_same_address_expect_equal_and_hashCode() {
        val email: Email = Email("user@email.com")
        val sameEmail: Email = Email("user@email.com")

        assertThat(email)
            .isEqualTo(sameEmail)
            .hasSameHashCodeAs(sameEmail)
    }
}