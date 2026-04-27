package io.github.raeperd.realworld.infrastructure.jwt

import io.github.raeperd.realworld.domain.jwt.JWTPayload

class UserJWTPayload internal constructor(private val sub: Long, private val name: String?, private val iat: Long) :
    JWTPayload {
    @Override
    fun getUserId(): Long {
        return sub
    }

    @Override
    fun isExpired(): Boolean {
        return iat < now().getEpochSecond()
    }

    @Override
    fun toString(): String? {
        return format("{\"sub\":%d,\"name\":\"%s\",\"iat\":%d}", sub, name, iat)
    }

    companion object {
        fun of(user: User, epochSecondExpired: Long): UserJWTPayload {
            return UserJWTPayload(user.getId(), valueOf(user.getEmail()), epochSecondExpired)
        }
    }
}
