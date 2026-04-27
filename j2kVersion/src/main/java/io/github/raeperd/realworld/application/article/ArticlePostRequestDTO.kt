package io.github.raeperd.realworld.application.article

import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeName("article")
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
@Value
internal class ArticlePostRequestDTO {
    @NotBlank
    var title: String? = null

    @NotBlank
    var description: String? = null

    @NotBlank
    var body: String? = null

    @NotNull
    var tagList: Set<Tag?>? = null

    fun toArticleContents(): ArticleContents? {
        return ArticleContents(description, ArticleTitle.of(title), body, tagList)
    }
}
