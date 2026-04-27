package io.github.raeperd.realworld.domain.article

import io.github.raeperd.realworld.domain.article.tag.Tag

@ExtendWith(MockitoExtension::class)
internal class ArticleServiceTest {
    private var articleService: ArticleService? = null

    @Mock
    private val userFindService: UserFindService? = null

    @Mock
    private val tagService: TagService? = null

    @Mock
    private val repository: ArticleRepository? = null

    @Spy
    private val author: User? = null

    @BeforeEach
    private fun initializeService() {
        articleService = ArticleService(userFindService, tagService, repository)
    }

    @Test
    fun when_author_not_found_expect_NoSuchElementException(@Mock contents: ArticleContents?) {
        `when`(userFindService.findById(anyLong())).thenReturn(empty())

        assertThatThrownBy({ articleService.createNewArticle(1L, contents) }
        ).isInstanceOf(NoSuchElementException::class.java)
    }

    @Test
    fun given_author_createNewArticle_then_tagService_reloadTags(
        @Mock contents: ArticleContents,
        @Mock tags: Set<Tag?>?
    ) {
        given(contents.getTags()).willReturn(tags)
        given(userFindService.findById(1L)).willReturn(of(author))
        given(repository.save(any())).willReturn(mock(Article::class.java))

        articleService.createNewArticle(1L, contents)

        then(tagService).should(times(1)).reloadAllTagsIfAlreadyPresent(tags)
    }

    @Test
    fun given_author_createNewArticle_then_author_writeArticle_contents(@Mock contents: ArticleContents?) {
        given(userFindService.findById(1L)).willReturn(of(author))
        given(repository.save(any())).willReturn(mock(Article::class.java))

        articleService.createNewArticle(1L, contents)

        then(author).should(times(1)).writeArticle(contents)
    }

    @Test
    fun given_author_writeArticle_then_userRepository_save(@Mock contents: ArticleContents?, @Mock article: Article?) {
        given(userFindService.findById(1L)).willReturn(of(author))
        given(author.writeArticle(contents)).willReturn(article)
        given(repository.save(article)).willReturn(article)

        articleService.createNewArticle(1L, contents)

        then(repository).should(times(1)).save(article)
    }

    @Test
    fun when_delete_article_notExists_expect_NoSuchElementException() {
        `when`(userFindService.findById(1L)).thenReturn(empty())

        assertThatThrownBy({ articleService.deleteArticleBySlug(1L, "not-exists") }
        ).isInstanceOf(NoSuchElementException::class.java)
    }
}