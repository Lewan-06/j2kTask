package io.github.raeperd.realworld.domain.user

import io.github.raeperd.realworld.domain.article.Article

@Table(name = "users")
@Entity
class User {
    @GeneratedValue(strategy = IDENTITY)
    @Id
    private val id: Long? = null

    @Embedded
    private var email: Email? = null

    @Embedded
    private var profile: Profile? = null

    @Embedded
    private var password: Password? = null

    @JoinTable(
        name = "user_followings",
        joinColumns = JoinColumn(name = "follower_id", referencedColumnName = "id"),
        inverseJoinColumns = JoinColumn(name = "followee_id", referencedColumnName = "id")
    )
    @OneToMany(cascade = REMOVE)
    private val followingUsers: Set<User?> = HashSet()

    @ManyToMany(mappedBy = "userFavorited")
    private val articleFavorited: Set<Article?> = HashSet()

    private constructor(email: Email?, profile: Profile?, password: Password?) {
        this.email = email
        this.profile = profile
        this.password = password
    }

    protected constructor()

    fun writeArticle(contents: ArticleContents?): Article? {
        return Article(this, contents)
    }

    fun updateArticle(article: Article, request: ArticleUpdateRequest?): Article? {
        if (article.getAuthor() !== this) {
            throw IllegalAccessError("Not authorized to update this article")
        }
        article.updateArticle(request)
        return article
    }

    fun writeCommentToArticle(article: Article, body: String?): Comment? {
        return article.addComment(this, body)
    }

    fun favoriteArticle(articleToFavorite: Article): Article? {
        articleFavorited.add(articleToFavorite)
        return articleToFavorite.afterUserFavoritesArticle(this)
    }

    fun unfavoriteArticle(articleToUnfavorite: Article): Article? {
        articleFavorited.remove(articleToUnfavorite)
        return articleToUnfavorite.afterUserUnFavoritesArticle(this)
    }

    fun followUser(followee: User?): User {
        followingUsers.add(followee)
        return this
    }

    fun unfollowUser(followee: User?): User {
        followingUsers.remove(followee)
        return this
    }

    fun deleteArticleComment(article: Article, commentId: Long) {
        article.removeCommentByUser(this, commentId)
    }

    fun viewArticleComments(article: Article): Set<Comment?>? {
        return article.getComments().stream()
            .map({ comment: Comment -> this.viewComment(comment) })
            .collect(toSet())
    }

    fun viewComment(comment: Comment): Comment? {
        viewProfile(comment.getAuthor())
        return comment
    }

    fun viewProfile(user: User): Profile? {
        return user.profile.withFollowing(followingUsers.contains(user))
    }

    fun getProfile(): Profile? {
        return profile
    }

    fun matchesPassword(rawPassword: String?, passwordEncoder: PasswordEncoder?): Boolean {
        return password.matchesPassword(rawPassword, passwordEncoder)
    }

    fun changeEmail(email: Email?) {
        this.email = email
    }

    fun changePassword(password: Password?) {
        this.password = password
    }

    fun changeName(userName: UserName?) {
        profile.changeUserName(userName)
    }

    fun changeBio(bio: String?) {
        profile.changeBio(bio)
    }

    fun changeImage(image: Image?) {
        profile.changeImage(image)
    }

    fun getId(): Long? {
        return id
    }

    fun getEmail(): Email? {
        return email
    }

    fun getName(): UserName? {
        return profile.getUserName()
    }

    fun getBio(): String? {
        return profile.getBio()
    }

    fun getImage(): Image? {
        return profile.getImage()
    }

    @Override
    fun equals(o: Object?): Boolean {
        if (this === o) return true
        if (o == null || getClass() !== o.getClass()) return false
        val user: User = o as User
        return email.equals(user.email)
    }

    @Override
    fun hashCode(): Int {
        return Objects.hash(email)
    }

    companion object {
        fun of(email: Email?, name: UserName?, password: Password?): User {
            return User(email, Profile(name), password)
        }
    }
}
