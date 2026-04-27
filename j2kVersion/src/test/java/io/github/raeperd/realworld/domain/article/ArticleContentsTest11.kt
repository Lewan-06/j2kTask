package io.github.raeperd.realworld.domain.article

import org.junit.jupiter.api.Test

@ExtendWith(MockitoExtension::class)
internal class ArticleContentsTest {
    @Test
    fun when_updateArticle_with_no_update_field_request_expect_not_changed() {
        val articleContents: ArticleContents = sampleArticleContents()
        val emptyUpdateRequest: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            builder().build()

        articleContents.updateArticleContentsIfPresent(emptyUpdateRequest)

        assertThatEqualArticleContents(articleContents, sampleArticleContents())
    }

    @Test
    fun when_updateArticle_with_all_field_expect_changed(@Mock titleToUpdate: ArticleTitle?) {
        val articleContents: ArticleContents = sampleArticleContents()
        val fullUpdateRequest: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            builder().titleToUpdate(titleToUpdate)
                .descriptionToUpdate("descriptionToUpdate")
                .bodyToUpdate("bodyToUpdate")
                .build()

        articleContents.updateArticleContentsIfPresent(fullUpdateRequest)

        assertThat(articleContents.getTitle()).isEqualTo(titleToUpdate)
        assertThat(articleContents.getDescription()).isEqualTo("descriptionToUpdate")
        assertThat(articleContents.getBody()).isEqualTo("bodyToUpdate")
    }

    private fun sampleArticleContents(): ArticleContents {
        return ArticleContents("description", ArticleTitle.of("title"), "body", emptySet())
    }

    private fun assertThatEqualArticleContents(left: ArticleContents, right: ArticleContents) {
        assertThat(equalsArticleContents(left, right)).isTrue()
    }

    private fun equalsArticleContents(left: ArticleContents, right: ArticleContents): Boolean {
        if (!left.getTitle().equals(right.getTitle())) {
            return false
        }
        if (!left.getDescription().equals(right.getDescription())) {
            return false
        }
        if (!left.getBody().equals(right.getBody())) {
            return false
        }
        return left.getTags().equals(right.getTags())
    }
}