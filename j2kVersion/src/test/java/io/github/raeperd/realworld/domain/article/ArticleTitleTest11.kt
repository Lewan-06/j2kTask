package io.github.raeperd.realworld.domain.article

import org.junit.jupiter.api.Test

internal class ArticleTitleTest {
    @Test
    fun when_create_with_title_expect_valid_slug() {
        val title: String = "\n\n Some?\t\n Title  ."
        val slugExpected: String = "some-title"

        val articleTitle: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            ArticleTitle.of(title)

        assertThat(articleTitle.getSlug()).isEqualTo(slugExpected)
    }

    @Test
    fun when_articleTitle_has_different_slug_expect_not_equal_and_hashCode() {
        val articleTitle: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            ArticleTitle.of("some title")
        val articleTitleWithDifferentSlug: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            ArticleTitle.of("other Title")

        assertThat(articleTitleWithDifferentSlug)
            .isNotEqualTo(articleTitle)
            .extracting(ArticleTitle::hashCode)
            .isNotEqualTo(articleTitle.hashCode())
    }

    @Test
    fun when_articleTitle_has_same_slug_expect_equal_and_hashCode() {
        val articleTitle: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            ArticleTitle.of("some title")
        val articleTitleWithSameSlug: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            ArticleTitle.of("Some Title")

        assertThat(articleTitleWithSameSlug)
            .isEqualTo(articleTitle)
            .hasSameHashCodeAs(articleTitle)
    }
}