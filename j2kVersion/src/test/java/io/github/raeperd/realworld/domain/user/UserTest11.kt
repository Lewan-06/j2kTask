package io.github.raeperd.realworld.domain.user

import org.junit.jupiter.api.Test

@ExtendWith(MockitoExtension::class)
internal class UserTest {
    @Mock
    private val passwordEncoder: PasswordEncoder? = null

    @Mock
    private val emailMock: Email? = null

    @Mock
    private val userNameMock: UserName? = null

    @Mock
    private val passwordMock: Password? = null

    @Test
    fun when_create_user_getImage_return_null() {
        val user: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            User.of(emailMock, userNameMock, passwordMock)

        assertThat(user.getImage()).isNull()
    }

    @Test
    fun when_create_user_getBio_return_null() {
        val user: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            User.of(emailMock, userNameMock, passwordMock)

        assertThat(user.getBio()).isNull()
    }

    @Test
    fun when_user_have_different_email_expect_not_equal_and_hashCode(
        @Mock otherEmail: Email?, @Mock otherName: UserName?, @Mock otherPassword: Password?
    ) {
        val user: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            User.of(emailMock, userNameMock, passwordMock)
        val userWithSameEmail: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            User.of(otherEmail, otherName, otherPassword)

        assertThat(userWithSameEmail)
            .isNotEqualTo(user)
            .extracting(User::hashCode)
            .isNotEqualTo(user.hashCode())
    }

    @Test
    fun when_user_have_same_email_expect_equal_and_hashCode(
        @Mock otherName: UserName?,
        @Mock otherPassword: Password?
    ) {
        val user: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            User.of(emailMock, userNameMock, passwordMock)
        val userWithSameEmail: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            User.of(emailMock, otherName, otherPassword)

        assertThat(userWithSameEmail)
            .isEqualTo(user)
            .hasSameHashCodeAs(user)
    }

    @Test
    fun when_view_profile_not_following_user_expect_following_false(@Mock otherEmail: Email?) {
        val user: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            User.of(emailMock, userNameMock, passwordMock)
        val otherUser: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            User.of(otherEmail, userNameMock, passwordMock)

        assertThat(user.viewProfile(otherUser))
            .hasFieldOrPropertyWithValue("following", false)
    }

    @Test
    fun when_matches_password_expect_password_matches_password() {
        val user: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            User.of(emailMock, userNameMock, passwordMock)

        user.matchesPassword("some-password", passwordEncoder)

        verify(passwordMock, times(1)).matchesPassword("some-password", passwordEncoder)
    }

    @Test
    fun when_changeEmail_expect_getEmail_return_new_email(@Mock emailToChange: Email?) {
        val user: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            User.of(emailMock, userNameMock, passwordMock)

        user.changeEmail(emailToChange)

        assertThat(user.getEmail()).isEqualTo(emailToChange)
    }

    @Test
    fun when_changePassword_expect_matchesPassword_matches_new_password(@Mock passwordToChange: Password?) {
        val user: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            User.of(emailMock, userNameMock, passwordMock)

        user.changePassword(passwordToChange)

        user.matchesPassword("some-password", passwordEncoder)
        verify(passwordToChange, times(1)).matchesPassword("some-password", passwordEncoder)
    }

    @Test
    fun when_changeName_expect_getName_return_new_name(@Mock userNameToChange: UserName?) {
        val user: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            User.of(emailMock, userNameMock, passwordMock)

        user.changeName(userNameToChange)

        assertThat(user.getName()).isEqualTo(userNameToChange)
    }

    @Test
    fun when_changeBio_expect_getBio_return_new_bio() {
        val user: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            User.of(emailMock, userNameMock, passwordMock)

        user.changeBio("new bio")

        assertThat(user.getBio()).isEqualTo("new bio")
    }

    @Test
    fun when_changeImage_expect_getImage_return_new_image(@Mock imageToChange: Image?) {
        val user: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            User.of(emailMock, userNameMock, passwordMock)

        user.changeImage(imageToChange)

        assertThat(user.getImage()).isEqualTo(imageToChange)
    }
}