package io.github.raeperd.realworld.domain.user

import java.util.Optional

class UserUpdateRequest private constructor(builder: UserUpdateRequestBuilder) {
    private val emailToUpdate: Email?
    private val userNameToUpdate: UserName?
    private val passwordToUpdate: String?
    private val imageToUpdate: Image?
    private val bioToUpdate: String?

    fun getEmailToUpdate(): Optional<Email?>? {
        return ofNullable(emailToUpdate)
    }

    fun getUserNameToUpdate(): Optional<UserName?>? {
        return ofNullable(userNameToUpdate)
    }

    fun getPasswordToUpdate(): Optional<String?>? {
        return ofNullable(passwordToUpdate)
    }

    fun getImageToUpdate(): Optional<Image?>? {
        return ofNullable(imageToUpdate)
    }

    fun getBioToUpdate(): Optional<String?>? {
        return ofNullable(bioToUpdate)
    }

    init {
        this.emailToUpdate = builder.emailToUpdate
        this.userNameToUpdate = builder.userNameToUpdate
        this.passwordToUpdate = builder.passwordToUpdate
        this.imageToUpdate = builder.imageToUpdate
        this.bioToUpdate = builder.bioToUpdate
    }

    class UserUpdateRequestBuilder {
        private var emailToUpdate: Email? = null
        private var userNameToUpdate: UserName? = null
        private var passwordToUpdate: String? = null
        private var imageToUpdate: Image? = null
        private var bioToUpdate: String? = null

        fun emailToUpdate(emailToUpdate: Email?): UserUpdateRequestBuilder {
            this.emailToUpdate = emailToUpdate
            return this
        }

        fun userNameToUpdate(userNameToUpdate: UserName?): UserUpdateRequestBuilder {
            this.userNameToUpdate = userNameToUpdate
            return this
        }

        fun passwordToUpdate(passwordToUpdate: String?): UserUpdateRequestBuilder {
            this.passwordToUpdate = passwordToUpdate
            return this
        }

        fun imageToUpdate(imageToUpdate: Image?): UserUpdateRequestBuilder {
            this.imageToUpdate = imageToUpdate
            return this
        }

        fun bioToUpdate(bioToUpdate: String?): UserUpdateRequestBuilder {
            this.bioToUpdate = bioToUpdate
            return this
        }

        fun build(): UserUpdateRequest {
            return UserUpdateRequest(this)
        }
    }

    companion object {
        fun builder(): UserUpdateRequestBuilder {
            return UserUpdateRequestBuilder()
        }
    }
}
