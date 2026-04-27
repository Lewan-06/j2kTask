package io.github.raeperd.realworld.domain.user

import org.junit.jupiter.api.Test

internal class ImageTest {
    @Test
    fun when_image_created_expect_toString_with_address() {
        val image: Image = Image("some-image-address")

        assertThat(image).hasToString("some-image-address")
    }

    @Test
    fun when_image_has_same_address_expect_equal_and_hashcode() {
        val image: Image = Image("address")
        val imageWithSameAddress: Image = Image("address")

        assertThat(imageWithSameAddress)
            .isEqualTo(image)
            .hasSameHashCodeAs(image)
    }
}