package io.github.raeperd.realworld.application.user

import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeName("user")
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
@Value
internal class UserPutRequestDTO {
    var email: String? = null
    var username: String? = null
    var password: String? = null
    var bio: String? = null
    var image: String? = null

    fun toUpdateRequest(): UserUpdateRequest? {
        return UserUpdateRequest.builder()
            .emailToUpdate(ofNullable(email).map({ Email() }).orElse(null))
            .userNameToUpdate(ofNullable(username).map({ UserName() }).orElse(null))
            .imageToUpdate(ofNullable(image).map({ Image() }).orElse(null))
            .passwordToUpdate(password)
            .bioToUpdate(bio)
            .build()
    }
}
