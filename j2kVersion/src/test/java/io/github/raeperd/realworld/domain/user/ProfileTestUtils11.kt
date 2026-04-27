package io.github.raeperd.realworld.domain.user

object ProfileTestUtils {
    fun profileOf(userName: UserName?, bio: String?, image: Image?, following: Boolean): Profile? {
        val profile: Profile = Profile(userName)
        profile.changeBio(bio)
        profile.changeImage(image)
        return profile.withFollowing(following)
    }
}
