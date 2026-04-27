package io.github.raeperd.realworld.domain.user

import javax.persistence.Column

@Embeddable
class Profile {
    @Embedded
    private var userName: UserName? = null

    @Column(name = "bio")
    private var bio: String? = null

    @Embedded
    private var image: Image? = null

    @Transient
    private var following: Boolean = false

    constructor(userName: UserName?) : this(userName, null, null, false)

    private constructor(userName: UserName?, bio: String?, image: Image?, following: Boolean) {
        this.userName = userName
        this.bio = bio
        this.image = image
        this.following = following
    }

    protected constructor()

    fun getUserName(): UserName? {
        return userName
    }

    fun getBio(): String? {
        return bio
    }

    fun getImage(): Image? {
        return image
    }

    fun isFollowing(): Boolean {
        return following
    }

    fun withFollowing(following: Boolean): Profile {
        this.following = following
        return this
    }

    fun changeUserName(userName: UserName?) {
        this.userName = userName
    }

    fun changeBio(bio: String?) {
        this.bio = bio
    }

    fun changeImage(image: Image?) {
        this.image = image
    }
}
