package io.github.raeperd.realworld.application.article

import io.github.raeperd.realworld.domain.article.ArticleService

@RestController
internal class ArticleRestController(articleService: ArticleService?) {
    private val articleService: ArticleService?

    init {
        this.articleService = articleService
    }

    @PostMapping("/articles")
    fun postArticle(
        @AuthenticationPrincipal jwtPayload: UserJWTPayload,
        @Valid @RequestBody dto: ArticlePostRequestDTO
    ): ArticleModel? {
        val articleCreated: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            articleService.createNewArticle(jwtPayload.getUserId(), dto.toArticleContents())
        return ArticleModel.fromArticle(articleCreated)
    }

    @GetMapping("/articles")
    fun getArticles(pageable: Pageable?): MultipleArticleModel? {
        val articles: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            articleService.getArticles(pageable)
        return MultipleArticleModel.fromArticles(articles)
    }

    @GetMapping(value = "/articles", params = ["author"])
    fun getArticlesByAuthor(@RequestParam author: String?, pageable: Pageable?): MultipleArticleModel? {
        val articles: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            articleService.getArticlesByAuthorName(author, pageable)
        return MultipleArticleModel.fromArticles(articles)
    }

    @GetMapping(value = "/articles", params = ["tag"])
    fun getArticlesByTag(@RequestParam tag: String?, pageable: Pageable?): MultipleArticleModel? {
        val articles: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            articleService.getArticlesByTag(tag, pageable)
        return MultipleArticleModel.fromArticles(articles)
    }

    @GetMapping(value = "/articles", params = ["favorited"])
    fun getArticleByFavoritedUsername(@RequestParam favorited: UserName?, pageable: Pageable?): MultipleArticleModel? {
        val articles: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            articleService.getArticleFavoritedByUsername(favorited, pageable)
        return MultipleArticleModel.fromArticles(articles)
    }

    @GetMapping("/articles/feed")
    fun getFeed(@AuthenticationPrincipal jwtPayload: UserJWTPayload, pageable: Pageable?): MultipleArticleModel? {
        val articles: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            articleService.getFeedByUserId(jwtPayload.getUserId(), pageable)
        return MultipleArticleModel.fromArticles(articles)
    }

    @GetMapping("/articles/{slug}")
    fun getArticleBySlug(@PathVariable slug: String?): ResponseEntity<ArticleModel?>? {
        return of(
            articleService.getArticleBySlug(slug)
                .map(ArticleModel::fromArticle)
        )
    }

    @PutMapping("/articles/{slug}")
    fun putArticleBySlug(
        @AuthenticationPrincipal jwtPayload: UserJWTPayload,
        @PathVariable slug: String?,
        @RequestBody dto: ArticlePutRequestDTO
    ): ArticleModel? {
        val articleUpdated: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            articleService.updateArticle(jwtPayload.getUserId(), slug, dto.toUpdateRequest())
        return ArticleModel.fromArticle(articleUpdated)
    }

    @PostMapping("/articles/{slug}/favorite")
    fun favoriteArticleBySlug(
        @AuthenticationPrincipal jwtPayload: UserJWTPayload,
        @PathVariable slug: String?
    ): ArticleModel? {
        val articleFavorited: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            articleService.favoriteArticle(jwtPayload.getUserId(), slug)
        return ArticleModel.fromArticle(articleFavorited)
    }

    @DeleteMapping("/articles/{slug}/favorite")
    fun unfavoriteArticleBySlug(
        @AuthenticationPrincipal jwtPayload: UserJWTPayload,
        @PathVariable slug: String?
    ): ArticleModel? {
        val articleUnfavored: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            articleService.unfavoriteArticle(jwtPayload.getUserId(), slug)
        return ArticleModel.fromArticle(articleUnfavored)
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/articles/{slug}")
    fun deleteArticleBySlug(
        @AuthenticationPrincipal jwtPayload: UserJWTPayload,
        @PathVariable slug: String?
    ) {
        articleService.deleteArticleBySlug(jwtPayload.getUserId(), slug)
    }
}
