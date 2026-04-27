package io.github.raeperd.realworld.application.tag

import io.github.raeperd.realworld.application.security.WithMockJWTUser

@ExtendWith(MockitoExtension::class)
@WebMvcTest(TagRestController::class)
internal class TagRestControllerTest {
    @Autowired
    private val mockMvc: MockMvc? = null

    @MockBean
    private val tagService: TagService? = null

    @MockBean
    private val jwtSerializer: JWTSerializer? = null

    @MockBean
    private val jwtDeserializer: JWTDeserializer? = null

    @WithMockJWTUser
    @Test
    @Throws(Exception::class)
    fun when_get_tags_expect_valid_tagsModel(@Mock tag: Tag) {
        `when`(tag.toString()).thenReturn("some-tag")
        `when`(tagService.findAll()).thenReturn(Set.of(tag))

        mockMvc.perform(get("/tags"))
            .andExpect(jsonPath("tags").isArray())
            .andExpect(jsonPath("tags", contains("some-tag")))
    }
}