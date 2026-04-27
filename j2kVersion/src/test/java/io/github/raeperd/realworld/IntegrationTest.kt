package io.github.raeperd.realworld

import com.fasterxml.jackson.databind.ObjectMapper

/*

Method name in this class follows /doc/Conduit.postman_collection.json

*/
@TestInstance(PER_CLASS)
@TestMethodOrder(OrderAnnotation::class)
@AutoConfigureMockMvc
@SpringBootTest
internal class IntegrationTest {
    @Autowired
    private val mockMvc: MockMvc? = null

    @Autowired
    private val objectMapper: ObjectMapper? = null

    private var token: String? = null
    private var commentId: Int = 0

    @Order(1)
    @Test
    @Throws(Exception::class)
    fun auth_register() {
        mockMvc.perform(
            post("/users")
                .contentType(APPLICATION_JSON)
                .content(
                    format(
                        "{\"user\":{\"email\":\"%s\", \"password\":\"%s\", \"username\":\"%s\"}}",
                        EMAIL,
                        PASSWORD,
                        USERNAME
                    )
                )
        )
            .andExpect(status().isOk())
            .andExpect(validUserModel())
    }

    @Order(2)
    @Test
    @Throws(Exception::class)
    fun auth_login() {
        mockMvc.perform(
            post("/users/login")
                .contentType(APPLICATION_JSON)
                .content(format("{\"user\":{\"email\":\"%s\", \"password\":\"%s\"}}", EMAIL, PASSWORD))
        )
            .andExpect(status().isOk())
            .andExpect(validUserModel())
    }

    @Order(3)
    @Test
    @Throws(Exception::class)
    fun auth_login_and_remember_token() {
        val contentAsString: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            mockMvc.perform(
                post("/users/login")
                    .contentType(APPLICATION_JSON)
                    .content(format("{\"user\":{\"email\":\"%s\", \"password\":\"%s\"}}", EMAIL, PASSWORD))
            )
                .andExpect(status().isOk())
                .andExpect(validUserModel())
                .andReturn().getResponse().getContentAsString()

        token = objectMapper.readTree(contentAsString).get("user").get("token").textValue()
    }

    @Order(4)
    @Test
    @Throws(Exception::class)
    fun auth_current_user() {
        mockMvc.perform(
            get("/user")
                .header(AUTHORIZATION, "Token " + token)
        )
            .andExpect(status().isOk())
            .andExpect(validUserModel())
    }

    @Order(5)
    @Test
    @Throws(Exception::class)
    fun auth_update_user() {
        mockMvc.perform(
            put("/user")
                .header(AUTHORIZATION, "Token " + token)
                .contentType(APPLICATION_JSON)
                .content(format("{\"user\":{\"email\":\"%s\"}}", EMAIL))
        )
            .andExpect(status().isOk())
            .andExpect(validUserModel())
    }

    @Order(6)
    @Test
    @Throws(Exception::class)
    fun profiles_register_celeb() {
        mockMvc.perform(
            post("/users")
                .contentType(APPLICATION_JSON)
                .content(
                    format(
                        "{\"user\":{\"email\":\"%s\", \"password\":\"%s\", \"username\":\"%s\"}}",
                        CELEB_EMAIL,
                        PASSWORD,
                        CELEB_USERNAME
                    )
                )
        )
            .andExpect(status().isOk())
            .andExpect(validUserModel())
    }

    @Order(7)
    @Test
    @Throws(Exception::class)
    fun profiles_profile() {
        mockMvc.perform(
            get("/profiles/{celeb_username}", CELEB_USERNAME)
                .header(AUTHORIZATION, "Token " + token)
        )
            .andExpect(status().isOk())
            .andExpect(validProfileModel())

        mockMvc.perform(get("/profiles/{celeb_username}", CELEB_USERNAME))
            .andExpect(status().isOk())
            .andExpect(validProfileModel())
    }

    @Order(8)
    @Test
    @Throws(Exception::class)
    fun follow_profile() {
        mockMvc.perform(
            post("/profiles/{celeb_username}/follow", CELEB_USERNAME)
                .header(AUTHORIZATION, "Token " + token)
        )
            .andExpect(status().isOk())
            .andExpect(validProfileModel())
            .andExpect(jsonPath("profile.following", `is`(true)))
    }

    @Order(9)
    @Test
    @Throws(Exception::class)
    fun unfollow_profile() {
        mockMvc.perform(
            delete("/profiles/{celeb_username}/follow", CELEB_USERNAME)
                .header(AUTHORIZATION, "Token " + token)
        )
            .andExpect(status().isOk())
            .andExpect(validProfileModel())
            .andExpect(jsonPath("profile.following", `is`(false)))
    }

    @Order(10)
    @Test
    @Throws(Exception::class)
    fun create_article() {
        mockMvc.perform(
            post("/articles")
                .header(AUTHORIZATION, "Token " + token)
                .contentType(APPLICATION_JSON)
                .content(
                    "{\n" +
                            "    \"article\": {\n" +
                            "        \"title\": \"How to train your dragon\",\n" +
                            "        \"description\": \"Ever wonder how?\",\n" +
                            "        \"body\": \"Very carefully.\",\n" +
                            "        \"tagList\": [\n" +
                            "            \"dragons\",\n" +
                            "            \"training\"\n" +
                            "        ]\n" +
                            "    }\n" +
                            "}"
                )
        )
            .andExpect(status().isOk())
            .andExpect(validSingleArticleModel())
    }

    @Order(11)
    @Test
    @Throws(Exception::class)
    fun get_all_articles() {
        mockMvc.perform(
            get("/articles?limit=20&offset=0")
                .header(AUTHORIZATION, "Token " + token)
        )
            .andExpect(status().isOk())
            .andExpect(validMultipleArticleModel())
    }

    @Order(11)
    @Test
    @Throws(Exception::class)
    fun get_all_articles_without_auth() {
        mockMvc.perform(get("/articles?limit=20&offset=0"))
            .andExpect(status().isOk())
            .andExpect(validMultipleArticleModel())
    }

    @Order(11)
    @Test
    @Throws(Exception::class)
    fun get_all_articles_with_author() {
        mockMvc.perform(
            get("/articles")
                .queryParam("author", USERNAME)
                .header(AUTHORIZATION, "Token " + token)
        )
            .andExpect(status().isOk())
            .andExpect(validMultipleArticleModel())
    }

    @Order(11)
    @Test
    @Throws(Exception::class)
    fun get_all_articles_with_tag() {
        mockMvc.perform(
            get("/articles")
                .queryParam("tag", "dragons")
                .header(AUTHORIZATION, "Token " + token)
        )
            .andExpect(status().isOk())
            .andExpect(validMultipleArticleModel())
    }

    @Order(11)
    @Test
    @Throws(Exception::class)
    fun get_single_article_by_slug() {
        mockMvc.perform(get("/articles/{slug}", "how-to-train-your-dragon"))
            .andExpect(status().isOk())
            .andExpect(validSingleArticleModel())
    }

    @Order(11)
    @Test
    @Throws(Exception::class)
    fun put_article() {
        mockMvc.perform(
            put("/articles/{slug}", "how-to-train-your-dragon")
                .header(AUTHORIZATION, "Token " + token)
                .contentType(APPLICATION_JSON)
                .content("{\"article\":{\"body\":\"With two hands\"}}")
        )
            .andExpect(status().isOk())
            .andExpect(validSingleArticleModel())
            .andExpect(jsonPath("article.body", `is`("With two hands")))
    }

    @Order(11)
    @Test
    @Throws(Exception::class)
    fun create_comments_for_article() {
        val contentAsString: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            mockMvc.perform(
                post("/articles/{slug}/comments", "how-to-train-your-dragon")
                    .header(AUTHORIZATION, "Token " + token)
                    .contentType(APPLICATION_JSON)
                    .content("{\"comment\":{\"body\":\"Thank you so much!\"}}")
            )
                .andExpect(status().isOk())
                .andExpect(validSingleCommentModel())
                .andExpect(jsonPath("comment.body", `is`("Thank you so much!")))
                .andReturn().getResponse().getContentAsString()

        commentId = objectMapper.readTree(contentAsString).get("comment").get("id").intValue()
    }

    @Order(12)
    @Test
    @Throws(Exception::class)
    fun all_comments_for_article() {
        mockMvc.perform(
            get("/articles/{slug}/comments", "how-to-train-your-dragon")
                .header(AUTHORIZATION, "Token " + token)
        )
            .andExpect(status().isOk())
            .andExpect(validMultipleCommentModel())
    }

    @Order(13)
    @Test
    @Throws(Exception::class)
    fun delete_comment_for_article() {
        mockMvc.perform(
            delete("/articles/{slug}/comments/{id}", "how-to-train-your-dragon", commentId)
                .header(AUTHORIZATION, "Token " + token)
        )
            .andExpect(status().isOk())
    }

    @Order(12)
    @Test
    @Throws(Exception::class)
    fun post_favorite_article() {
        mockMvc.perform(
            post("/articles/{slug}/favorite", "how-to-train-your-dragon")
                .header(AUTHORIZATION, "Token " + token)
        )
            .andExpect(status().isOk())
            .andExpect(validSingleArticleModel())
    }

    @Order(13)
    @Test
    @Throws(Exception::class)
    fun get_articles_favorited_by_username() {
        mockMvc.perform(
            get("/articles?favorited={username}", USERNAME)
                .header(AUTHORIZATION, "Token " + token)
        )
            .andExpect(status().isOk())
            .andExpect(validMultipleArticleModel())
            .andExpect(jsonPath("articles[0].favorited", `is`(true)))
    }

    @Order(13)
    @Test
    @Throws(Exception::class)
    fun get_articles_favorited_by_username_not_exists() {
        mockMvc.perform(
            get("/articles?favorited={username}", "jane")
                .header(AUTHORIZATION, "Token " + token)
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("articles").isEmpty())
            .andExpect(jsonPath("articlesCount", `is`(0)))
    }

    @Order(13)
    @Test
    @Throws(Exception::class)
    fun get_feed() {
        mockMvc.perform(
            get("/articles/feed")
                .header(AUTHORIZATION, "Token " + token)
        )
            .andExpect(status().isOk())
            .andExpect(validMultipleArticleModel())
            .andExpect(jsonPath("articles[0].favorited", `is`(true)))
    }

    @Order(14)
    @Test
    @Throws(Exception::class)
    fun unfavorite_article() {
        mockMvc.perform(
            delete("/articles/{slug}/favorite", "how-to-train-your-dragon")
                .header(AUTHORIZATION, "Token " + token)
        )
            .andExpect(status().isOk())
            .andExpect(validSingleArticleModel())
            .andExpect(jsonPath("article.favorited", `is`(false)))
    }

    @Order(15)
    @Test
    @Throws(Exception::class)
    fun delete_article() {
        mockMvc.perform(
            delete("/articles/{slug}", "how-to-train-your-dragon")
                .header(AUTHORIZATION, "Token " + token)
        )
            .andExpect(status().isNoContent())
    }
}
