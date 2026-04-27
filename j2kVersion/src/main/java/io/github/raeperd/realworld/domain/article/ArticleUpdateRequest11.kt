package io.github.raeperd.realworld.domain.article

import java.util.Optional

class ArticleUpdateRequest private constructor(
    titleToUpdate: ArticleTitle?,
    descriptionToUpdate: String?,
    bodyToUpdate: String?
) {
    private val titleToUpdate: ArticleTitle?
    private val descriptionToUpdate: String?
    private val bodyToUpdate: String?

    fun getTitleToUpdate(): Optional<ArticleTitle?>? {
        return ofNullable(titleToUpdate)
    }

    fun getDescriptionToUpdate(): Optional<String?>? {
        return ofNullable(descriptionToUpdate)
    }

    fun getBodyToUpdate(): Optional<String?>? {
        return ofNullable(bodyToUpdate)
    }

    private constructor(builder: ArticleUpdateRequestBuilder) : this(
        builder.titleToUpdate,
        builder.descriptionToUpdate,
        builder.bodyToUpdate
    )

    init {
        this.titleToUpdate = titleToUpdate
        this.descriptionToUpdate = descriptionToUpdate
        this.bodyToUpdate = bodyToUpdate
    }

    class ArticleUpdateRequestBuilder {
        private var titleToUpdate: ArticleTitle? = null
        private var descriptionToUpdate: String? = null
        private var bodyToUpdate: String? = null

        fun titleToUpdate(titleToUpdate: ArticleTitle?): ArticleUpdateRequestBuilder {
            this.titleToUpdate = titleToUpdate
            return this
        }

        fun descriptionToUpdate(descriptionToUpdate: String?): ArticleUpdateRequestBuilder {
            this.descriptionToUpdate = descriptionToUpdate
            return this
        }

        fun bodyToUpdate(bodyToUpdate: String?): ArticleUpdateRequestBuilder {
            this.bodyToUpdate = bodyToUpdate
            return this
        }

        fun build(): ArticleUpdateRequest {
            return ArticleUpdateRequest(this)
        }
    }

    companion object {
        fun builder(): ArticleUpdateRequestBuilder {
            return ArticleUpdateRequestBuilder()
        }
    }
}
