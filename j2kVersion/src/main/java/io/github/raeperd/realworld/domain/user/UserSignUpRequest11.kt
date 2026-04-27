package io.github.raeperd.realworld.domain.user

import io.github.raeperd.realworld.domain.user.UserName

class UserSignUpRequest(email: Email?, userName: UserName?, rawPassword: String?) {
    private val email: Email?
    private val userName: UserName?
    private val rawPassword: String?

    init {
        this.email = email
        this.userName = userName
        this.rawPassword = rawPassword
    }

    fun getEmail(): Email? {
        return email
    }

    fun getUserName(): UserName? {
        return userName
    }

    fun getRawPassword(): String? {
        return rawPassword
    }
}
