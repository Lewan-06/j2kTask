package io.github.raeperd.realworld.application.user

import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeName("user")
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
@Value
internal class UserModel {
    var email: String? = null
    var username: String? = null
    var token: String? = null
    var bio: String? = null
    var image: String? = null

    companion object {
        fun fromUserAndToken(user: User, token: String?): UserModel {
            return UserModel(
                valueOf(user.getEmail()),
                valueOf(user.getName()),
                token,
                "",
                ""
            )
        }
    }
}
