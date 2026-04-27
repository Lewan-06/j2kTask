package io.github.raeperd.realworld.application.article.comment

import io.github.raeperd.realworld.application.article.comment.CommentModel.CommentModelNested

@Value
internal class MultipleCommentModel {
    var comments: List<CommentModelNested?>? = null

    companion object {
        fun fromComments(comments: Set<Comment?>): MultipleCommentModel {
            val commentsCollected: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
                comments.stream().map(CommentModelNested::fromComment)
                    .collect(toList())
            return MultipleCommentModel(commentsCollected)
        }
    }
}
