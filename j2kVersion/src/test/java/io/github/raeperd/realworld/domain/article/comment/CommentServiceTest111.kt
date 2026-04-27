package io.github.raeperd.realworld.domain.article.comment

import io.github.raeperd.realworld.domain.article.Article

@ExtendWith(MockitoExtension::class)
internal class CommentServiceTest {
    private var commentService: CommentService? = null

    @Mock
    private val userFindService: UserFindService? = null

    @Mock
    private val articleFindService: ArticleFindService? = null

    @BeforeEach
    private fun initializeService() {
        commentService = CommentService(userFindService, articleFindService)
    }

    @Test
    fun when_articleFindService_return_empty_expect_NoSuchElementException() {
        `when`(articleFindService.getArticleBySlug("slug")).thenReturn(empty())

        assertThatThrownBy({ commentService.deleteCommentById(1L, "slug", 2L) }
        ).isInstanceOf(NoSuchElementException::class.java)
    }

    @Test
    fun when_articleFindService_return_empty_expect_NoSuchElementException_without_userId() {
        `when`(articleFindService.getArticleBySlug("slug")).thenReturn(empty())

        assertThatThrownBy({ commentService.getComments("slug") }).isInstanceOf(NoSuchElementException::class.java)
    }

    @Test
    fun given_articleFindService_return_article_then_return_comments_of_article(
        @Mock article: Article,
        @Mock comment: Comment?
    ) {
        given(articleFindService.getArticleBySlug("slug")).willReturn(of(article))
        given(article.getComments()).willReturn(Set.of(comment))

        assertThat(commentService.getComments("slug")).contains(comment)

        then(article).should(times(1)).getComments()
    }

    @Test
    fun when_userFindService_return_empty_expect_NoSuchElementException(@Mock article: Article?) {
        `when`(articleFindService.getArticleBySlug("slug")).thenReturn(of(article))
        `when`(userFindService.findById(1L)).thenReturn(empty())

        assertThatThrownBy({ commentService.deleteCommentById(1L, "slug", 2L) }
        ).isInstanceOf(NoSuchElementException::class.java)
    }

    @Test
    fun given_user_and_article_deleteCommentById_then_user_deleteArticleComment(
        @Mock user: User?,
        @Mock article: Article?
    ) {
        given(userFindService.findById(1L)).willReturn(of(user))
        given(articleFindService.getArticleBySlug("slug")).willReturn(of(article))

        commentService.deleteCommentById(1L, "slug", 2L)

        then(user).should(times(1)).deleteArticleComment(article, 2L)
    }
}