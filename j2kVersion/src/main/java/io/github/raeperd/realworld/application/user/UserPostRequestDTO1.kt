package io.github.raeperd.realworld.application.user

import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeName("user")
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
@Value
internal class UserPostRequestDTO {
    @javax.validation.constraints.Email
    var email: String? = null

    @NotBlank
    var username: String? = null

    @NotBlank
    var password: String? = null

    fun toSignUpRequest(): UserSignUpRequest? {
        return UserSignUpRequest(
            Email(email),
            UserName(username),
            password
        )
    }
}
