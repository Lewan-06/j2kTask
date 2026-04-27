package io.github.raeperd.realworld.application.article

import io.github.raeperd.realworld.application.user.ProfileModel.ProfileModelNested

@Value
internal class ArticleModel {
    var article: ArticleModelNested? = null

    @Value
    internal class ArticleModelNested {
        var slug: String? = null
        var title: String? = null
        var description: String? = null
        var body: String? = null
        var tagList: Set<String?>? = null
        var createdAt: ZonedDateTime? = null
        var updatedAt: ZonedDateTime? = null
        var favorited: Boolean = false
        var favoritesCount: Int = 0
        var author: ProfileModelNested? = null

        companion object {
            fun fromArticle(article: Article): ArticleModelNested {
                val contents: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
                    article.getContents()
                val titleFromArticle: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
                    contents.getTitle()
                return ArticleModelNested(
                    titleFromArticle.getSlug(), titleFromArticle.getTitle(),
                    contents.getDescription(), contents.getBody(),
                    contents.getTags().stream().map(Tag::toString).collect(toSet()),
                    article.getCreatedAt().atZone(ZoneId.of("Asia/Seoul")),
                    article.getUpdatedAt().atZone(ZoneId.of("Asia/Seoul")),
                    article.isFavorited(), article.getFavoritedCount(),
                    ProfileModelNested.fromProfile(article.getAuthor().getProfile())
                )
            }
        }
    }

    companion object {
        fun fromArticle(article: Article): ArticleModel {
            return ArticleModel(ArticleModelNested.fromArticle(article))
        }
    }
}
