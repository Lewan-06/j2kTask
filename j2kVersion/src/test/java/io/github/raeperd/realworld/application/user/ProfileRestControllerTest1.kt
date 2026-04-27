package io.github.raeperd.realworld.application.user

import io.github.raeperd.realworld.IntegrationTestUtils

@WebMvcTest(ProfileRestController::class)
internal class ProfileRestControllerTest {
    @Autowired
    private val mockMvc: MockMvc? = null

    @MockBean
    private val profileService: ProfileService? = null

    @MockBean
    private val jwtDeserializer: JWTDeserializer? = null

    @Test
    @Throws(Exception::class)
    fun when_get_profile_with_not_exists_username_expect_notFound_status() {
        `when`(profileService.viewProfile(any(UserName::class.java))).thenThrow(NoSuchElementException::class.java)

        mockMvc.perform(get("/profiles/{username}", "user-name-not-exists"))
            .andExpect(status().isNotFound())
    }

    @Test
    @Throws(Exception::class)
    fun when_get_profile_with_username_expect_valid_ProfileModel() {
        `when`(profileService.viewProfile(UserName("sample-user-name"))).thenReturn(sampleProfile())

        mockMvc.perform(get("/profiles/{username}", "sample-user-name"))
            .andExpect(status().isOk())
            .andExpect(IntegrationTestUtils.validProfileModel())
    }

    @WithMockJWTUser
    @Test
    @Throws(Exception::class)
    fun when_get_profile_with_auth_and_not_exists_username_expect_notFound_status() {
        `when`(
            profileService.viewProfile(
                anyLong(),
                any(UserName::class.java)
            )
        ).thenThrow(NoSuchElementException::class.java)

        mockMvc.perform(get("/profiles/{username}", "user-name-not-exists"))
            .andExpect(status().isNotFound())
    }

    @WithMockJWTUser
    @Test
    @Throws(Exception::class)
    fun when_get_profile_with_username_and_auth_expect_valid_ProfileModel() {
        `when`(profileService.viewProfile(anyLong(), eq(UserName("sample-username")))).thenReturn(sampleProfile())

        mockMvc.perform(get("/profiles/{username}", "sample-username"))
            .andExpect(status().isOk())
            .andExpect(IntegrationTestUtils.validProfileModel())
    }

    @Test
    @Throws(Exception::class)
    fun when_follow_user_without_authentication_expect_status_forbidden() {
        mockMvc.perform(post("/profiles/{username}/follow", "sample-username"))
            .andExpect(status().isForbidden())
    }

    @WithMockJWTUser
    @Test
    @Throws(Exception::class)
    fun when_follow_user_expect_profileService_followAndViewProfile_called() {
        `when`(
            profileService.followAndViewProfile(
                anyLong(),
                eq(UserName("sample-username"))
            )
        ).thenReturn(sampleProfile())

        mockMvc.perform(post("/profiles/{username}/follow", "sample-username"))
            .andExpect(status().isOk())
            .andExpect(IntegrationTestUtils.validProfileModel())
    }

    private fun sampleProfile(): Profile? {
        return ProfileTestUtils.profileOf(
            UserName("sample-user-name"),
            "sample-bio",
            Image("sample-image"),
            false
        )
    }
}
