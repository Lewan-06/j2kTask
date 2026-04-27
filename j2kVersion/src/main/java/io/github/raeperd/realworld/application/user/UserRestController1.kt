package io.github.raeperd.realworld.application.user

import io.github.raeperd.realworld.domain.jwt.JWTSerializer

@RestController
internal class UserRestController(userService: UserService?, jwtSerializer: JWTSerializer?) {
    private val userService: UserService?
    private val jwtSerializer: JWTSerializer?

    init {
        this.userService = userService
        this.jwtSerializer = jwtSerializer
    }

    @PostMapping("/users")
    fun postUser(@Valid @RequestBody dto: UserPostRequestDTO): UserModel? {
        val userSaved: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            userService.signUp(dto.toSignUpRequest())
        return fromUserAndToken(userSaved, jwtSerializer.jwtFromUser(userSaved))
    }

    @PostMapping("/users/login")
    fun loginUser(@Valid @RequestBody dto: UserLoginRequestDTO): ResponseEntity<UserModel?>? {
        return of(
            userService.login(Email(dto.getEmail()), dto.getPassword())
                .map({ user -> fromUserAndToken(user, jwtSerializer.jwtFromUser(user)) })
        )
    }

    @GetMapping("/user")
    fun getUser(@AuthenticationPrincipal jwtPayload: UserJWTPayload): ResponseEntity<UserModel?>? {
        return of(
            userService.findById(jwtPayload.getUserId())
                .map({ user -> UserModel.fromUserAndToken(user, getCurrentCredential()) })
        )
    }

    @PutMapping("/user")
    fun putUser(
        @AuthenticationPrincipal jwtPayload: UserJWTPayload,
        @Valid @RequestBody dto: UserPutRequestDTO
    ): UserModel? {
        val userUpdated: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            userService.updateUser(jwtPayload.getUserId(), dto.toUpdateRequest())
        return fromUserAndToken(userUpdated, getCurrentCredential())
    }

    companion object {
        private fun getCurrentCredential(): String? {
            return SecurityContextHolder.getContext()
                .getAuthentication()
                .getCredentials()
                .toString()
        }
    }
}
