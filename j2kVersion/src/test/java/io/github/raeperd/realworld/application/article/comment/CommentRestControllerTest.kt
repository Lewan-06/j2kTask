package io.github.raeperd.realworld.application.article.comment

import io.github.raeperd.realworld.application.security.WithMockJWTUser

@WebMvcTest(CommentRestController::class)
internal class CommentRestControllerTest {
    @MockBean
    private val commentService: CommentService? = null

    @MockBean
    private val jwtDeserializer: JWTDeserializer? = null

    @Autowired
    private val mockMvc: MockMvc? = null

    @Test
    @Throws(Exception::class)
    fun when_get_comments_without_auth_expect_called_commentService() {
        val ARTICLE_SLUG: String = "article-slug"
        `when`(commentService.getComments(ARTICLE_SLUG)).thenReturn(emptySet())

        mockMvc.perform(get("/articles/" + ARTICLE_SLUG + "/comments"))
            .andExpect(status().isOk())

        then(commentService).should(times(1)).getComments(ARTICLE_SLUG)
    }

    @WithMockJWTUser
    @Test
    @Throws(Exception::class)
    fun when_get_comments_with_auth_expect_called_commentService() {
        val ARTICLE_SLUG: String = "article-slug"
        `when`(commentService.getComments(anyLong(), eq(ARTICLE_SLUG))).thenReturn(emptySet())

        mockMvc.perform(get("/articles/" + ARTICLE_SLUG + "/comments"))
            .andExpect(status().isOk())

        then(commentService).should(times(1)).getComments(anyLong(), eq(ARTICLE_SLUG))
    }
}