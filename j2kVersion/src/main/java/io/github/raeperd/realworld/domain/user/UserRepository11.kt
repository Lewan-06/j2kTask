package io.github.raeperd.realworld.domain.user

import org.springframework.data.repository.Repository

internal interface UserRepository : Repository<User?, Long?> {
    fun save(user: User?): User?

    fun findById(id: Long): Optional<User?>?
    fun findFirstByEmail(email: Email?): Optional<User?>?
    fun findFirstByProfileUserName(userName: UserName?): Optional<User?>?
}
