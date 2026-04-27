package io.github.raeperd.realworld.application.article.comment

import io.github.raeperd.realworld.domain.article.comment.CommentService

@RestController
internal class CommentRestController(commentService: CommentService?) {
    private val commentService: CommentService?

    init {
        this.commentService = commentService
    }

    @PostMapping("/articles/{slug}/comments")
    fun postComments(
        @AuthenticationPrincipal jwtPayload: UserJWTPayload,
        @PathVariable slug: String?, @Valid @RequestBody dto: CommentPostRequestDTO
    ): CommentModel? {
        val commentAdded: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            commentService.createComment(jwtPayload.getUserId(), slug, dto.getBody())
        return CommentModel.fromComment(commentAdded)
    }

    @GetMapping("/articles/{slug}/comments")
    fun getComments(
        @AuthenticationPrincipal jwtPayload: UserJWTPayload?,
        @PathVariable slug: String?
    ): MultipleCommentModel? {
        val comments: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            ofNullable(jwtPayload)
                .map(JWTPayload::getUserId)
                .map({ userId -> commentService.getComments(userId, slug) })
                .orElseGet({ commentService.getComments(slug) })
        return MultipleCommentModel.fromComments(comments)
    }

    @DeleteMapping("/articles/{slug}/comments/{id}")
    fun deleteComment(
        @AuthenticationPrincipal jwtPayload: UserJWTPayload,
        @PathVariable slug: String?, @PathVariable id: Long
    ) {
        commentService.deleteCommentById(jwtPayload.getUserId(), slug, id)
    }
}
