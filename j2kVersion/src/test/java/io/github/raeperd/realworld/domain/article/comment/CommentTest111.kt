package io.github.raeperd.realworld.domain.article.comment

import io.github.raeperd.realworld.domain.article.Article

@ExtendWith(MockitoExtension::class)
internal class CommentTest {
    @MethodSource("provideDifferentComments")
    @ParameterizedTest
    fun when_compare_different_comment_expect_not_equal(commentLeft: Comment?, commentRight: Comment) {
        assertThat(commentLeft).isNotEqualTo(commentRight)
            .extracting(Comment::hashCode)
            .isNotEqualTo(commentRight.hashCode())
    }

    @Test
    fun when_compare_same_comment_expect_equal_and_hashCode(@Mock article: Article?, @Mock author: User?) {
        val now: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? = Instant.now()
        val commentLeft: Comment = commentWithCreatedAt(article, author, "body", now)
        val commentRight: Comment = commentWithCreatedAt(article, author, "body", now)

        assertThat(commentLeft)
            .isEqualTo(commentRight)
            .hasSameHashCodeAs(commentRight)
    }

    companion object {
        private fun provideDifferentComments(): Stream<Arguments?>? {
            val articleSample: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? = mock(
                Article::class.java
            )
            val authorSample: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? = mock(
                User::class.java
            )
            val bodySample: String = "bodySample"
            val createAtSample: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? = now()
            val commentSample: Comment = commentWithCreatedAt(articleSample, authorSample, bodySample, createAtSample)
            return Stream.of(
                Pair.of(
                    commentSample,
                    commentWithCreatedAt(mock(Article::class.java), authorSample, bodySample, createAtSample)
                ),
                Pair.of(
                    commentSample,
                    commentWithCreatedAt(articleSample, mock(User::class.java), bodySample, createAtSample)
                ),
                Pair.of(
                    commentSample,
                    commentWithCreatedAt(articleSample, authorSample, "different body", createAtSample)
                ),
                Pair.of(
                    commentSample,
                    commentWithCreatedAt(articleSample, authorSample, bodySample, now().plusSeconds(10))
                )
            )
                .map({ pair -> Arguments.of(pair.getFirst(), pair.getSecond()) })
        }

        private fun commentWithCreatedAt(
            article: Article?,
            author: User?,
            body: String?,
            createdAt: Instant?
        ): Comment {
            val comment: Comment = Comment(article, author, body)
            ReflectionTestUtils.setField(comment, "createdAt", createdAt)
            return comment
        }
    }
}