package io.github.raeperd.realworld.application.user

import io.github.raeperd.realworld.domain.jwt.JWTPayload

@RequestMapping("/profiles")
@RestController
internal class ProfileRestController(profileService: ProfileService?) {
    private val profileService: ProfileService?

    init {
        this.profileService = profileService
    }

    @GetMapping("/{username}")
    fun getProfileByUsername(
        @AuthenticationPrincipal jwtPayload: UserJWTPayload?,
        @PathVariable username: UserName?
    ): ProfileModel? {
        return ofNullable(jwtPayload)
            .map(JWTPayload::getUserId)
            .map({ viewerId -> profileService.viewProfile(viewerId, username) })
            .map(ProfileModel::fromProfile)
            .orElseGet({ fromProfile(profileService.viewProfile(username)) })
    }

    @PostMapping("/{username}/follow")
    fun followUser(
        @AuthenticationPrincipal followerPayload: UserJWTPayload,
        @PathVariable username: UserName?
    ): ProfileModel? {
        return fromProfile(
            profileService.followAndViewProfile(followerPayload.getUserId(), username)
        )
    }

    @DeleteMapping("/{username}/follow")
    fun unfollowUser(
        @AuthenticationPrincipal followerPayload: UserJWTPayload,
        @PathVariable username: UserName?
    ): ProfileModel? {
        return fromProfile(
            profileService.unfollowAndViewProfile(followerPayload.getUserId(), username)
        )
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NoSuchElementException::class)
    fun handleNoSuchElementException(exception: NoSuchElementException?) {
        // return NOT FOUND status
    }
}
