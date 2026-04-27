package io.github.raeperd.realworld.application.article.comment

import io.github.raeperd.realworld.application.user.ProfileModel.ProfileModelNested

@Value
internal class CommentModel {
    var comment: CommentModelNested? = null

    @Value
    internal class CommentModelNested {
        var id: Long = 0
        var body: String? = null
        var createdAt: ZonedDateTime? = null
        var updatedAt: ZonedDateTime? = null
        var author: ProfileModelNested? = null

        companion object {
            fun fromComment(comment: Comment): CommentModelNested {
                return CommentModelNested(
                    comment.getId(),
                    comment.getBody(),
                    comment.getCreatedAt().atZone(ZoneId.of("Asia/Seoul")),
                    comment.getUpdatedAt().atZone(ZoneId.of("Asia/Seoul")),
                    ProfileModelNested.fromProfile(comment.getAuthor().getProfile())
                )
            }
        }
    }

    companion object {
        fun fromComment(comment: Comment): CommentModel {
            return CommentModel(CommentModelNested.fromComment(comment))
        }
    }
}
