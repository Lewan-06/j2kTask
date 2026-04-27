package io.github.raeperd.realworld

import org.springframework.test.web.servlet.ResultMatcher

object IntegrationTestUtils {
    private val ISO_8601_PATTERN: Pattern? =
        compile("^\\d{4,}-[01]\\d-[0-3]\\dT[0-2]\\d:[0-5]\\d:[0-5]\\d.\\d+(?:[+-][0-2]\\d:[0-5]\\d|Z)$")

    val EMAIL: String = "user@email.com"
    val PASSWORD: String = "password"
    val USERNAME: String = "username"

    val CELEB_EMAIL: String = "celeb_" + EMAIL
    val CELEB_USERNAME: String = "celeb_" + USERNAME

    fun validUserModel(): ResultMatcher? {
        return matchAll(
            jsonPath("user").isMap(),
            jsonPath("user.email").isString(),
            jsonPath("user.token").isString(),
            jsonPath("user.username").isString(),
            jsonPath("user.bio").isString(),
            jsonPath("user.image").isString()
        )
    }

    fun validProfileModel(): ResultMatcher? {
        return validProfileModelInPath("profile")
    }

    private fun validProfileModelInPath(path: String?): ResultMatcher? {
        return matchAll(
            jsonPath(path).isMap(),
            jsonPath(path.toString() + ".username").isString(),
            jsonPath(path.toString() + ".bio").hasJsonPath(),
            jsonPath(path.toString() + ".image").hasJsonPath(),
            jsonPath(path.toString() + ".following").isBoolean()
        )
    }

    fun validSingleArticleModel(): ResultMatcher? {
        return matchAll(
            jsonPath("article").isMap(),
            validArticleModelInPath("article")
        )
    }

    fun validMultipleArticleModel(): ResultMatcher? {
        return matchAll(
            jsonPath("articles").isArray(),
            jsonPath("articlesCount").isNumber(),
            validArticleModelInPath("articles[0]")
        )
    }

    private fun validArticleModelInPath(path: String?): ResultMatcher? {
        return matchAll(
            jsonPath(path.toString() + ".slug").isString(),
            jsonPath(path.toString() + ".title").isString(),
            jsonPath(path.toString() + ".description").isString(),
            jsonPath(path.toString() + ".body").isString(),
            jsonPath(path.toString() + ".tagList").isNotEmpty(),
            jsonPath(path.toString() + ".createdAt", matchesPattern(ISO_8601_PATTERN)),
            jsonPath(path.toString() + ".updatedAt", matchesPattern(ISO_8601_PATTERN)),
            jsonPath(path.toString() + ".favorited").isBoolean(),
            jsonPath(path.toString() + ".favoritesCount").isNumber(),
            validProfileModelInPath(path.toString() + ".author")
        )
    }

    fun validSingleCommentModel(): ResultMatcher? {
        return matchAll(
            jsonPath("comment").isMap(),
            validCommentModelInPath("comment")
        )
    }

    fun validMultipleCommentModel(): ResultMatcher? {
        return matchAll(
            jsonPath("comments").isArray(),
            validCommentModelInPath("comments[0]")
        )
    }

    private fun validCommentModelInPath(path: String?): ResultMatcher? {
        return matchAll(
            jsonPath(path.toString() + ".id").isNumber(),
            jsonPath(path.toString() + ".body").isString(),
            jsonPath(path.toString() + ".createdAt", matchesPattern(ISO_8601_PATTERN)),
            jsonPath(path.toString() + ".updatedAt", matchesPattern(ISO_8601_PATTERN)),
            validProfileModelInPath(path.toString() + ".author")
        )
    }
}
