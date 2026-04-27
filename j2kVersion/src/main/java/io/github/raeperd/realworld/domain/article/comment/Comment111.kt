package io.github.raeperd.realworld.domain.article.comment

import io.github.raeperd.realworld.domain.article.Article

@Table(name = "comments")
@EntityListeners(AuditingEntityListener::class)
@Entity
class Comment {
    @GeneratedValue(strategy = IDENTITY)
    @Id
    private val id: Long? = null

    @JoinColumn(name = "article_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = EAGER)
    private var article: Article? = null

    @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = EAGER)
    private var author: User? = null

    @Column(name = "created_at")
    @CreatedDate
    private val createdAt: Instant? = null

    @Column(name = "updated_at")
    @LastModifiedDate
    private val updatedAt: Instant? = null

    @Column(name = "body", nullable = false)
    private var body: String? = null

    constructor(article: Article?, author: User?, body: String?) {
        this.article = article
        this.author = author
        this.body = body
    }

    protected constructor()

    fun getId(): Long? {
        return id
    }

    fun getAuthor(): User? {
        return author
    }

    fun getCreatedAt(): Instant? {
        return createdAt
    }

    fun getUpdatedAt(): Instant? {
        return updatedAt
    }

    fun getBody(): String? {
        return body
    }

    @Override
    fun equals(o: Object?): Boolean {
        if (this === o) return true
        if (o == null || getClass() !== o.getClass()) return false
        val comment: Comment = o as Comment
        return article.equals(comment.article) && author.equals(comment.author) && Objects.equals(
            createdAt,
            comment.createdAt
        ) && body.equals(comment.body)
    }

    @Override
    fun hashCode(): Int {
        return Objects.hash(article, author, createdAt, body)
    }
}
