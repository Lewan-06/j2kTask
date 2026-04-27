package io.github.raeperd.realworld.domain.article

import io.github.raeperd.realworld.domain.user.User

@ExtendWith(MockitoExtension::class)
internal class ArticleTest {
    @Mock
    private val contents: ArticleContents? = null

    @Mock
    private val title: ArticleTitle? = null

    @Mock
    private val author: User? = null

    @Test
    fun when_article_has_different_author_expect_not_equal_and_hashCode(@Mock otherUser: User?) {
        `when`(contents.getTitle()).thenReturn(title)

        val article: Article = Article(author, contents)
        val articleFromOtherUser: Article = Article(otherUser, contents)

        assertThat(articleFromOtherUser)
            .isNotEqualTo(article)
            .extracting(Article::hashCode)
            .isNotEqualTo(article.hashCode())
    }

    @Test
    fun when_article_has_different_contents_expect_not_equal_and_hashCode(
        @Mock otherContents: ArticleContents,
        @Mock otherTitle: ArticleTitle?
    ) {
        `when`(contents.getTitle()).thenReturn(title)
        `when`(otherContents.getTitle()).thenReturn(otherTitle)

        val article: Article = Article(author, contents)
        val articleWithOtherContents: Article = Article(author, otherContents)

        assertThat(articleWithOtherContents)
            .isNotEqualTo(article)
            .extracting(Article::hashCode)
            .isNotEqualTo(article.hashCode())
    }

    @Test
    fun when_article_has_same_author_and_title_expect_equal_and_hashCode() {
        `when`(contents.getTitle()).thenReturn(title)

        val article: Article = Article(author, contents)
        val articleWithSameAuthorSlug: Article = Article(author, contents)

        assertThat(articleWithSameAuthorSlug)
            .isEqualTo(article)
            .hasSameHashCodeAs(article)
    }
}