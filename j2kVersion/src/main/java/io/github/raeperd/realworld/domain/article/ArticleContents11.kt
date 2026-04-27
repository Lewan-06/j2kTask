package io.github.raeperd.realworld.domain.article

import io.github.raeperd.realworld.domain.article.tag.Tag

@Embeddable
class ArticleContents {
    @Embedded
    private var title: ArticleTitle? = null

    @Column(nullable = false)
    private var description: String? = null

    @Column(nullable = false)
    private var body: String? = null

    @JoinTable(
        name = "articles_tags",
        joinColumns = JoinColumn(name = "article_id", referencedColumnName = "id", nullable = false),
        inverseJoinColumns = JoinColumn(name = "tag_id", referencedColumnName = "id", nullable = false)
    )
    @ManyToMany(fetch = EAGER, cascade = PERSIST)
    private var tags: Set<Tag?>? = HashSet()

    constructor(description: String?, title: ArticleTitle?, body: String?, tags: Set<Tag?>?) {
        this.description = description
        this.title = title
        this.body = body
        this.tags = tags
    }

    protected constructor()

    fun getTitle(): ArticleTitle? {
        return title
    }

    fun getDescription(): String? {
        return description
    }

    fun getBody(): String? {
        return body
    }

    fun getTags(): Set<Tag?>? {
        return tags
    }

    fun updateArticleContentsIfPresent(updateRequest: ArticleUpdateRequest) {
        updateRequest.getTitleToUpdate().ifPresent({ titleToUpdate -> title = titleToUpdate })
        updateRequest.getDescriptionToUpdate().ifPresent({ descriptionToUpdate -> description = descriptionToUpdate })
        updateRequest.getBodyToUpdate().ifPresent({ bodyToUpdate -> body = bodyToUpdate })
    }

    fun setTags(tags: Set<Tag?>?) {
        this.tags = tags
    }
}
