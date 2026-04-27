package io.github.raeperd.realworld.domain.article

import io.github.raeperd.realworld.domain.article.tag.TagService

@Service
class ArticleService internal constructor(
    userFindService: UserFindService?,
    tagService: TagService?,
    articleRepository: ArticleRepository?
) :
    ArticleFindService {
    private val userFindService: UserFindService?
    private val tagService: TagService?
    private val articleRepository: ArticleRepository?

    init {
        this.userFindService = userFindService
        this.tagService = tagService
        this.articleRepository = articleRepository
    }

    @Transactional
    fun createNewArticle(authorId: Long, contents: ArticleContents): Article? {
        val tagsReloaded: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            tagService.reloadAllTagsIfAlreadyPresent(contents.getTags())
        contents.setTags(tagsReloaded)
        return userFindService.findById(authorId)
            .map({ author -> author.writeArticle(contents) })
            .map(articleRepository::save)
            .orElseThrow({ NoSuchElementException() })
    }

    @Transactional(readOnly = true)
    fun getArticles(pageable: Pageable?): Page<Article?>? {
        return articleRepository.findAll(pageable)
    }

    @Transactional(readOnly = true)
    fun getFeedByUserId(userId: Long, pageable: Pageable?): Page<Article?>? {
        return userFindService.findById(userId)
            .map({ user ->
                articleRepository.findAllByUserFavoritedContains(user, pageable)
                    .map({ article -> article.updateFavoriteByUser(user) })
            })
            .orElseThrow({ NoSuchElementException() })
    }

    @Transactional(readOnly = true)
    fun getArticleFavoritedByUsername(username: UserName?, pageable: Pageable?): Page<Article?>? {
        return userFindService.findByUsername(username)
            .map({ user ->
                articleRepository.findAllByUserFavoritedContains(user, pageable)
                    .map({ article -> article.updateFavoriteByUser(user) })
            })
            .orElse(Page.empty())
    }

    @Transactional(readOnly = true)
    fun getArticlesByAuthorName(authorName: String?, pageable: Pageable?): Page<Article?>? {
        return articleRepository.findAllByAuthorProfileUserName(UserName(authorName), pageable)
    }

    @Transactional(readOnly = true)
    fun getArticlesByTag(tagValue: String?, pageable: Pageable?): Page<Article?>? {
        return tagService.findByValue(tagValue)
            .map({ tag -> articleRepository.findAllByContentsTagsContains(tag, pageable) })
            .orElse(Page.empty())
    }

    @Override
    @Transactional(readOnly = true)
    fun getArticleBySlug(slug: String?): Optional<Article?>? {
        return articleRepository.findFirstByContentsTitleSlug(slug)
    }

    @Transactional
    fun updateArticle(userId: Long, slug: String?, request: ArticleUpdateRequest?): Article? {
        return mapIfAllPresent(userFindService.findById(userId), getArticleBySlug(slug),
            { user, article -> user.updateArticle(article, request) })
            .orElseThrow({ NoSuchElementException() })
    }

    @Transactional
    fun favoriteArticle(userId: Long, articleSlugToFavorite: String?): Article? {
        return mapIfAllPresent(
            userFindService.findById(userId), getArticleBySlug(articleSlugToFavorite),
            User::favoriteArticle
        )
            .orElseThrow({ NoSuchElementException() })
    }

    @Transactional
    fun unfavoriteArticle(userId: Long, articleSlugToUnFavorite: String?): Article? {
        return mapIfAllPresent(
            userFindService.findById(userId), getArticleBySlug(articleSlugToUnFavorite),
            User::unfavoriteArticle
        )
            .orElseThrow({ NoSuchElementException() })
    }

    @Transactional
    fun deleteArticleBySlug(userId: Long, slug: String?) {
        userFindService.findById(userId)
            .ifPresentOrElse({ user -> articleRepository.deleteArticleByAuthorAndContentsTitleSlug(user, slug) },
                { throw NoSuchElementException() })
    }
}
