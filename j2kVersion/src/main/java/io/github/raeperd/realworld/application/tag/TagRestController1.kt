package io.github.raeperd.realworld.application.tag

import io.github.raeperd.realworld.domain.article.tag.TagService

@RequestMapping("/tags")
@RestController
internal class TagRestController(tagService: TagService?) {
    private val tagService: TagService?

    init {
        this.tagService = tagService
    }

    @GetMapping
    fun getTags(): TagsModel? {
        return TagsModel(
            tagService.findAll().stream()
                .map(Objects::toString)
                .collect(toSet())
        )
    }
}
