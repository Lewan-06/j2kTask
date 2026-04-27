package io.github.raeperd.realworld.application.article

import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeName("article")
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
@Value
internal class ArticlePutRequestDTO {
    var title: String? = null
    var description: String? = null
    var body: String? = null

    fun toUpdateRequest(): ArticleUpdateRequest? {
        return builder().titleToUpdate(ofNullable(title).map(ArticleTitle::of).orElse(null))
            .descriptionToUpdate(description)
            .bodyToUpdate(body)
            .build()
    }
}
