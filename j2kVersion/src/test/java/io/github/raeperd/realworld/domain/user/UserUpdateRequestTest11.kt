package io.github.raeperd.realworld.domain.user

import org.junit.jupiter.api.Test

@ExtendWith(MockitoExtension::class)
internal class UserUpdateRequestTest {
    @Test
    fun when_userUpdateRequest_created_without_field_expect_all_null() {
        val requestWithoutFields: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            builder().build()

        assertThat(requestWithoutFields).hasAllNullFieldsOrProperties()
    }

    @Test
    fun when_userUpdateRequest_created_without_field_expect_get_return_empty() {
        val requestWithoutFields: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            builder().build()

        assertThat(requestWithoutFields.getEmailToUpdate()).isEmpty()
        assertThat(requestWithoutFields.getUserNameToUpdate()).isEmpty()
        assertThat(requestWithoutFields.getPasswordToUpdate()).isEmpty()
        assertThat(requestWithoutFields.getImageToUpdate()).isEmpty()
        assertThat(requestWithoutFields.getBioToUpdate()).isEmpty()
    }

    @Test
    fun when_userUpdateRequest_created_with_all_field_expect_all_fields(
        @Mock emailToUpdate: Email?,
        @Mock userNameToUpdate: UserName?,
        @Mock imageToUpdate: Image?
    ) {
        val requestWithAllField: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            builder().emailToUpdate(emailToUpdate)
                .userNameToUpdate(userNameToUpdate)
                .passwordToUpdate("passwordToUpdate")
                .imageToUpdate(imageToUpdate)
                .bioToUpdate("bioToUpdate")
                .build()

        assertThat(requestWithAllField)
            .hasFieldOrPropertyWithValue("emailToUpdate", emailToUpdate)
            .hasFieldOrPropertyWithValue("userNameToUpdate", userNameToUpdate)
            .hasFieldOrPropertyWithValue("passwordToUpdate", "passwordToUpdate")
            .hasFieldOrPropertyWithValue("imageToUpdate", imageToUpdate)
            .hasFieldOrPropertyWithValue("bioToUpdate", "bioToUpdate")
    }

    @Test
    fun when_userUpdateRequest_created_with_all_field_expect_get_return_field(
        @Mock emailToUpdate: Email?,
        @Mock userNameToUpdate: UserName?,
        @Mock imageToUpdate: Image?
    ) {
        val requestWithAllField: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            builder().emailToUpdate(emailToUpdate)
                .userNameToUpdate(userNameToUpdate)
                .passwordToUpdate("passwordToUpdate")
                .imageToUpdate(imageToUpdate)
                .bioToUpdate("bioToUpdate")
                .build()

        assertThat(requestWithAllField.getEmailToUpdate()).contains(emailToUpdate)
        assertThat(requestWithAllField.getUserNameToUpdate()).contains(userNameToUpdate)
        assertThat(requestWithAllField.getPasswordToUpdate()).contains("passwordToUpdate")
        assertThat(requestWithAllField.getImageToUpdate()).contains(imageToUpdate)
        assertThat(requestWithAllField.getBioToUpdate()).contains("bioToUpdate")
    }
}