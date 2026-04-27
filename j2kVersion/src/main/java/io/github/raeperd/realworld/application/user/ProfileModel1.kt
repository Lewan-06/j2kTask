package io.github.raeperd.realworld.application.user

import io.github.raeperd.realworld.domain.user.Profile

@Value
class ProfileModel {
    var profile: ProfileModelNested? = null

    @Value
    class ProfileModelNested {
        var username: String? = null
        var bio: String? = null
        var image: String? = null
        var following: Boolean = false

        companion object {
            fun fromProfile(profile: Profile): ProfileModelNested {
                return ProfileModelNested(
                    valueOf(profile.getUserName()),
                    profile.getBio(),
                    valueOf(profile.getImage()),
                    profile.isFollowing()
                )
            }
        }
    }

    companion object {
        fun fromProfile(profile: Profile): ProfileModel {
            return ProfileModel(ProfileModelNested.fromProfile(profile))
        }
    }
}
