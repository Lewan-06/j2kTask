package io.github.raeperd.realworld.domain.article.comment

import io.github.raeperd.realworld.domain.article.Article

@Service
class CommentService internal constructor(userFindService: UserFindService?, articleFindService: ArticleFindService?) {
    private val userFindService: UserFindService?
    private val articleFindService: ArticleFindService?

    init {
        this.userFindService = userFindService
        this.articleFindService = articleFindService
    }

    @Transactional
    fun createComment(userId: Long, slug: String?, body: String?): Comment? {
        return mapIfAllPresent(userFindService.findById(userId), articleFindService.getArticleBySlug(slug),
            { user, article -> user.writeCommentToArticle(article, body) })
            .orElseThrow({ NoSuchElementException() })
    }

    @Transactional(readOnly = true)
    fun getComments(userId: Long, slug: String?): Set<Comment?>? {
        return mapIfAllPresent(
            userFindService.findById(userId), articleFindService.getArticleBySlug(slug),
            User::viewArticleComments
        )
            .orElseThrow({ NoSuchElementException() })
    }

    @Transactional(readOnly = true)
    fun getComments(slug: String?): Set<Comment?>? {
        return articleFindService.getArticleBySlug(slug)
            .map(Article::getComments)
            .orElseThrow({ NoSuchElementException() })
    }

    @Transactional
    fun deleteCommentById(userId: Long, slug: String?, commentId: Long) {
        val articleContainsComments: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            articleFindService.getArticleBySlug(slug)
                .orElseThrow({ NoSuchElementException() })
        userFindService.findById(userId)
            .ifPresentOrElse({ user -> user.deleteArticleComment(articleContainsComments, commentId) },
                { throw NoSuchElementException() })
    }
}
