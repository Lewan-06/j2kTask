package io.github.raeperd.realworld.application.article

import io.github.raeperd.realworld.application.article.ArticleModel.ArticleModelNested

@Value
internal class MultipleArticleModel {
    var articles: List<ArticleModelNested?>? = null
    var articlesCount: Int = 0

    companion object {
        fun fromArticles(articles: Page<Article?>): MultipleArticleModel {
            val articlesCollected: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
                articles.map(ArticleModelNested::fromArticle)
                    .stream().collect(toList())
            return MultipleArticleModel(articlesCollected, articlesCollected.size())
        }
    }
}
