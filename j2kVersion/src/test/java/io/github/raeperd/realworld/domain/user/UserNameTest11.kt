package io.github.raeperd.realworld.domain.user

import org.junit.jupiter.api.Test

internal class UserNameTest {
    @Test
    fun when_userName_created_expect_toString_with_name() {
        val userName: UserName = UserName("name")

        assertThat(userName).hasToString("name")
    }

    @Test
    fun when_userName_has_same_name_expect_equal_and_hashcode() {
        val userName: UserName = UserName("name")
        val userNameWithSameName: UserName = UserName("name")

        assertThat(userNameWithSameName)
            .isEqualTo(userName)
            .hasSameHashCodeAs(userName)
    }
}