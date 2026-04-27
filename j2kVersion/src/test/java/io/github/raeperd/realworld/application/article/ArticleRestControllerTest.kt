package io.github.raeperd.realworld.application.article

import com.fasterxml.jackson.databind.ObjectMapper

@WithMockJWTUser
@WebMvcTest(ArticleRestController::class)
internal class ArticleRestControllerTest {
    @MockBean
    private val articleService: ArticleService? = null

    @MockBean
    private val jwtDeserializer: JWTDeserializer? = null

    @Autowired
    private val mockMvc: MockMvc? = null

    @Autowired
    private val objectMapper: ObjectMapper? = null


    @MethodSource("provideInvalidPostDTO")
    @ParameterizedTest
    @Throws(Exception::class)
    fun when_post_article_with_invalid_body_expect_status_badRequest(invalidDTO: ArticlePostRequestDTO?) {
        mockMvc.perform(
            post("/articles")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDTO))
        )
            .andExpect(status().isBadRequest())
    }

    companion object {
        private fun provideInvalidPostDTO(): Stream<Arguments?>? {
            return Stream.of(
                Arguments.of(ArticlePostRequestDTO(null, "description", "body", emptySet())),
                Arguments.of(ArticlePostRequestDTO("title", null, "body", emptySet())),
                Arguments.of(ArticlePostRequestDTO("title", "description", null, emptySet())),
                Arguments.of(ArticlePostRequestDTO("title", "description", "body", null)),
                Arguments.of(ArticlePostRequestDTO(" ", "description", "body", emptySet())),
                Arguments.of(ArticlePostRequestDTO("title", " ", "body", emptySet())),
                Arguments.of(ArticlePostRequestDTO("title", "description", " ", emptySet()))
            )
        }
    }
}