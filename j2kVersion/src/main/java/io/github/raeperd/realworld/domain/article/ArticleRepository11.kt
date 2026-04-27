package io.github.raeperd.realworld.domain.article

import io.github.raeperd.realworld.domain.article.tag.Tag

internal interface ArticleRepository : Repository<Article?, Long?> {
    fun save(article: Article?): Article?

    fun findAll(pageable: Pageable?): Page<Article?>?
    fun findAllByUserFavoritedContains(user: User?, pageable: Pageable?): Page<Article?>?
    fun findAllByAuthorProfileUserName(authorName: UserName?, pageable: Pageable?): Page<Article?>?
    fun findAllByContentsTagsContains(tag: Tag?, pageable: Pageable?): Page<Article?>?
    fun findFirstByContentsTitleSlug(slug: String?): Optional<Article?>?

    fun deleteArticleByAuthorAndContentsTitleSlug(author: User?, slug: String?)
}
