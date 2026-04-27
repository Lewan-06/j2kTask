package io.github.raeperd.realworld.application.user

import com.fasterxml.jackson.databind.ObjectMapper

@WebMvcTest(UserRestController::class)
internal class UserRestControllerTest {
    @Autowired
    private val mockMvc: MockMvc? = null

    @Autowired
    private val objectMapper: ObjectMapper? = null

    @MockBean
    private val userService: UserService? = null

    @MockBean
    private val jwtSerializer: JWTSerializer? = null

    @MockBean
    private val jwtDeserializer: JWTDeserializer? = null

    @BeforeEach
    fun mockJwtSerializer() {
        `when`(jwtSerializer.jwtFromUser(any())).thenReturn("MOCKED_TOKEN")
    }

    @MethodSource("provideInvalidPostDTO")
    @ParameterizedTest
    @Throws(Exception::class)
    fun when_post_user_with_invalid_body_expect_status_badRequest(dto: UserPostRequestDTO?) {
        mockMvc.perform(
            post("/users")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(dto))
        )
            .andExpect(status().isBadRequest())
    }

    @Test
    @Throws(Exception::class)
    fun when_post_user_expect_valid_userModel() {
        `when`(userService.signUp(any(UserSignUpRequest::class.java))).thenReturn(sampleUser())

        mockMvc.perform(
            post("/users")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(samplePostDTO()))
        )
            .andExpect(status().isOk())
            .andExpect(IntegrationTestUtils.validUserModel())
    }

    @Test
    @Throws(Exception::class)
    fun when_login_user_expect_valid_userModel() {
        `when`(userService.login(Email("user@email.com"), "password")).thenReturn(of(sampleUser()))

        mockMvc.perform(
            post("/users/login")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sampleLoginDTO()))
        )
            .andExpect(status().isOk())
            .andExpect(IntegrationTestUtils.validUserModel())
    }

    @Test
    @Throws(Exception::class)
    fun when_login_with_invalid_authorization_header_expect_ignore_token() {
        `when`(userService.login(Email("user@email.com"), "password")).thenReturn(of(sampleUser()))

        mockMvc.perform(
            post("/users/login")
                .header(AUTHORIZATION, "Token token-invalid")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sampleLoginDTO()))
        )
            .andExpect(status().isOk())
            .andExpect(IntegrationTestUtils.validUserModel())
    }

    @WithMockJWTUser
    @Test
    @Throws(Exception::class)
    fun when_get_user_expect_valid_userModel() {
        `when`(userService.findById(anyLong())).thenReturn(of(sampleUser()))

        mockMvc.perform(
            get("/user")
                .accept(APPLICATION_JSON)
        )
            .andExpect(status().isOk())
            .andExpect(IntegrationTestUtils.validUserModel())
    }

    @WithMockJWTUser
    @Test
    @Throws(Exception::class)
    fun when_put_user_expect_status_ok() {
        `when`(userService.updateUser(anyLong(), any(UserUpdateRequest::class.java))).thenReturn(sampleUser())

        mockMvc.perform(
            put("/user")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(samplePutDTO()))
        )
            .andExpect(status().isOk())
            .andExpect(IntegrationTestUtils.validUserModel())
    }

    private fun sampleUser(): User? {
        return userWithEmailAndName("user@email.com", "username")
    }

    private fun sampleLoginDTO(): UserLoginRequestDTO? {
        return UserLoginRequestDTO("user@email.com", "password")
    }

    private fun samplePostDTO(): UserPostRequestDTO? {
        return UserPostRequestDTO("user@email.com", "username", "password")
    }

    private fun samplePutDTO(): UserPutRequestDTO? {
        return UserPutRequestDTO("new-user@email.com", null, null, null, null)
    }

    companion object {
        private fun provideInvalidPostDTO(): Stream<Arguments?>? {
            return Stream.of(
                Arguments.of(UserPostRequestDTO("not-email", "username", "password")),
                Arguments.of(UserPostRequestDTO("user@email.com", "", "password")),
                Arguments.of(UserPostRequestDTO("user@email.com", "username", ""))
            )
        }
    }
}