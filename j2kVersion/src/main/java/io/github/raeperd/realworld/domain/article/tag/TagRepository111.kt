package io.github.raeperd.realworld.domain.article.tag

import org.springframework.data.repository.Repository

internal interface TagRepository : Repository<Tag?, Long?> {
    fun findAll(): List<Tag?>?

    fun findFirstByValue(value: String?): Optional<Tag?>?
}
