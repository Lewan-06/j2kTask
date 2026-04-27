package io.github.raeperd.realworld.domain.article.tag

import org.springframework.stereotype.Service

@Service
class TagService internal constructor(tagRepository: TagRepository?) {
    private val tagRepository: TagRepository?

    init {
        this.tagRepository = tagRepository
    }

    @Transactional(readOnly = true)
    fun findAll(): Set<Tag?>? {
        return HashSet(tagRepository.findAll())
    }

    @Transactional(readOnly = true)
    fun reloadAllTagsIfAlreadyPresent(tags: Set<Tag?>): Set<Tag?>? {
        return tags.stream()
            .map({ tag -> findByValue(valueOf(tag)).orElse(tag) })
            .collect(toSet())
    }

    @Transactional(readOnly = true)
    fun findByValue(value: String?): Optional<Tag?>? {
        return tagRepository.findFirstByValue(value)
    }
}
