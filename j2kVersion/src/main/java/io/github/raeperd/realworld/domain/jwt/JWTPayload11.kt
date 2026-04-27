package io.github.raeperd.realworld.domain.jwt

import java.io.Serializable

interface JWTPayload : Serializable {
    fun getUserId(): Long
    fun isExpired(): Boolean
}
