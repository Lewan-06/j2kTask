package io.github.raeperd.realworld.domain.article

import org.junit.jupiter.api.Test

@EnableJpaAuditing
@DataJpaTest
internal class ArticleRepositoryTest {
    @Autowired
    private val repository: ArticleRepository? = null

    @MethodSource("provideInvalidArticle")
    @ParameterizedTest
    fun when_save_invalid_article_expect_DataIntegrityViolationException(invalidArticle: Article?) {
        assertThatThrownBy({ repository.save(invalidArticle) }
        ).isInstanceOf(DataIntegrityViolationException::class.java)
    }

    @Test
    fun when_save_article_expect_auditing_works() {
        val contentsToSave: ArticleContents =
            ArticleContents("description", ArticleTitle.of("some title"), "body", emptySet())
        val articleToSave: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            databaseUser().writeArticle(contentsToSave)

        val articleSaved: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            repository.save(articleToSave)

        assertThat(articleSaved).hasNoNullFieldsOrProperties()
    }

    companion object {
        private fun provideInvalidArticle(): Stream<Arguments?>? {
            return provideInvalidArticleContents()
                .map({ invalidArticleContents -> Article(databaseUser(), invalidArticleContents) })
                .map(Arguments::of)
        }

        private fun provideInvalidArticleContents(): Stream<ArticleContents?>? {
            return Stream.of(
                ArticleContents(null, null, null, emptySet()),
                ArticleContents("description", null, null, emptySet()),
                ArticleContents(null, ArticleTitle.of("title"), null, emptySet()),
                ArticleContents(null, null, "body", emptySet())
            )
        }
    }
}