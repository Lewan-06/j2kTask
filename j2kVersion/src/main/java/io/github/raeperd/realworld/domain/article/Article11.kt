package io.github.raeperd.realworld.domain.article

import io.github.raeperd.realworld.domain.article.comment.Comment

@Table(name = "articles")
@EntityListeners(AuditingEntityListener::class)
@Entity
class Article {
    @GeneratedValue(strategy = IDENTITY)
    @Id
    private val id: Long? = null

    @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = EAGER)
    private var author: User? = null

    @Embedded
    private var contents: ArticleContents? = null

    @Column(name = "created_at")
    @CreatedDate
    private val createdAt: Instant? = null

    @Column(name = "updated_at")
    @LastModifiedDate
    private val updatedAt: Instant? = null

    @JoinTable(
        name = "article_favorites",
        joinColumns = JoinColumn(name = "article_id", referencedColumnName = "id", nullable = false),
        inverseJoinColumns = JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    )
    @ManyToMany(fetch = EAGER, cascade = PERSIST)
    private val userFavorited: Set<User?> = HashSet()

    @OneToMany(mappedBy = "article", cascade = [PERSIST, REMOVE])
    private val comments: Set<Comment?> = HashSet()

    @Transient
    private var favorited: Boolean = false

    constructor(author: User?, contents: ArticleContents?) {
        this.author = author
        this.contents = contents
    }

    protected constructor()

    fun afterUserFavoritesArticle(user: User?): Article? {
        userFavorited.add(user)
        return updateFavoriteByUser(user)
    }

    fun afterUserUnFavoritesArticle(user: User?): Article? {
        userFavorited.remove(user)
        return updateFavoriteByUser(user)
    }

    fun addComment(author: User?, body: String?): Comment? {
        val commentToAdd: Comment = Comment(this, author, body)
        comments.add(commentToAdd)
        return commentToAdd
    }

    fun removeCommentByUser(user: User, commentId: Long) {
        val commentsToDelete: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            comments.stream()
                .filter({ comment -> comment.getId().equals(commentId) })
                .findFirst()
                .orElseThrow({ NoSuchElementException() })
        if (!user.equals(author) || !user.equals(commentsToDelete.getAuthor())) {
            throw IllegalAccessError("Not authorized to delete comment")
        }
        comments.remove(commentsToDelete)
    }

    fun updateArticle(updateRequest: ArticleUpdateRequest?) {
        contents.updateArticleContentsIfPresent(updateRequest)
    }

    fun updateFavoriteByUser(user: User?): Article {
        favorited = userFavorited.contains(user)
        return this
    }

    fun getAuthor(): User? {
        return author
    }

    fun getContents(): ArticleContents? {
        return contents
    }

    fun getCreatedAt(): Instant? {
        return createdAt
    }

    fun getUpdatedAt(): Instant? {
        return updatedAt
    }

    fun getFavoritedCount(): Int {
        return userFavorited.size()
    }

    fun isFavorited(): Boolean {
        return favorited
    }

    fun getComments(): Set<Comment?>? {
        return comments
    }

    @Override
    fun equals(o: Object?): Boolean {
        if (this === o) return true
        if (o == null || getClass() !== o.getClass()) return false
        val article: Article = o as Article
        return author.equals(article.author) && contents.getTitle().equals(article.contents.getTitle())
    }

    @Override
    fun hashCode(): Int {
        return Objects.hash(author, contents.getTitle())
    }
}
