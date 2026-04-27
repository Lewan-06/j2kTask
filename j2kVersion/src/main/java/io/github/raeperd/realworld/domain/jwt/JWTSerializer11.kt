package io.github.raeperd.realworld.domain.jwt

import io.github.raeperd.realworld.domain.user.User

interface JWTSerializer {
    fun jwtFromUser(user: User?): String?
}
