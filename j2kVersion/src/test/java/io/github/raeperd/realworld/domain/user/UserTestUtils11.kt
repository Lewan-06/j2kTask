package io.github.raeperd.realworld.domain.user

import org.springframework.test.util.ReflectionTestUtils

object UserTestUtils {
    fun userWithEmailAndName(email: String?, name: String?): User? {
        return User.of(
            Email(email),
            UserName(name),
            null
        )
    }

    fun userWithIdAndEmail(id: Long, email: String?): User? {
        val sampleUser: User? = userWithEmailAndName(email, "name")
        ReflectionTestUtils.setField(sampleUser, "id", id)
        return sampleUser
    }

    fun databaseUser(): User? {
        val password: Password = Password()
        ReflectionTestUtils.setField(password, "encodedPassword", "$2y$10\$Uw0vceuCbx3bVOsXZuP")
        val databaseUser: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? = User.of(
            Email("databaseUser@email.com"),
            UserName("databaseUser"),
            password
        )
        ReflectionTestUtils.setField(databaseUser, "id", 1L)
        return databaseUser
    }
}
