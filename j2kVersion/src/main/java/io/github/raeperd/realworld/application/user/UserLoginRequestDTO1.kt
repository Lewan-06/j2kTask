package io.github.raeperd.realworld.application.user

import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeName("user")
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
@Value
internal class UserLoginRequestDTO {
    @Email
    var email: String? = null

    @NotBlank
    var password: String? = null
}
