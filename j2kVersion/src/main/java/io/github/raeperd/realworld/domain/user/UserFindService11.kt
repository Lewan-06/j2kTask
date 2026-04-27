package io.github.raeperd.realworld.domain.user

import java.util.Optional

interface UserFindService {
    fun findById(id: Long): Optional<User?>?
    fun findByUsername(userName: UserName?): Optional<User?>?
}