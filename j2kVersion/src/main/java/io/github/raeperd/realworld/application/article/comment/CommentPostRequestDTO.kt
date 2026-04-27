package io.github.raeperd.realworld.application.article.comment

import com.fasterxml.jackson.annotation.JsonCreator

@JsonTypeName("comment")
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
@Getter
internal class CommentPostRequestDTO @JsonCreator constructor(@field:NotBlank private val body: String?)
