package io.github.raeperd.realworld.domain.user

import org.junit.jupiter.api.Test

@DataJpaTest
internal class UserRepositoryTest {
    @Autowired
    private val userRepository: UserRepository? = null

    @Test
    fun when_save_user_expect_saved() {
        val userToSave: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? = User.of(
            Email("user@email.com"),
            UserName("name"),
            Password.of("rawPassword", PASSWORD_ENCODER)
        )

        assertThat(userRepository.save(userToSave)).hasNoNullFieldsOrProperties()
    }

    @Test
    fun when_save_user_with_image_expect_saved() {
        val userToSave: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? = User.of(
            Email("user@email.com"),
            UserName("name"),
            Password.of("rawPassword", PASSWORD_ENCODER)
        )
        val imageToSave: Image = Image("some-image")

        userToSave.changeImage(imageToSave)

        assertThat(userRepository.save(userToSave))
            .extracting(User::getImage)
            .isEqualTo(imageToSave)
    }

    companion object {
        private val PASSWORD_ENCODER: PasswordEncoder = BCryptPasswordEncoder()
    }
}