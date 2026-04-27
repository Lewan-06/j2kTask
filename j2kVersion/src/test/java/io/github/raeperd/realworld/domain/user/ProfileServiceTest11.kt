package io.github.raeperd.realworld.domain.user

import org.junit.jupiter.api.BeforeEach

@ExtendWith(MockitoExtension::class)
internal class ProfileServiceTest {
    private var profileService: ProfileService? = null

    @Mock
    private val userFindService: UserFindService? = null

    @BeforeEach
    private fun initializeService() {
        this.profileService = ProfileService(userFindService)
    }

    @Test
    fun when_viewProfile_with_viewer_not_exists_expect_NoSuchElementException(@Mock userName: UserName?) {
        `when`(userFindService.findById(1L)).thenReturn(empty())

        assertThatThrownBy({ profileService.viewProfile(1L, userName) }
        ).isInstanceOf(NoSuchElementException::class.java)
    }

    @Test
    fun when_viewProfile_with_not_exists_username_expect_NoSuchElementException(
        @Mock user: User?,
        @Mock userName: UserName?
    ) {
        `when`(userFindService.findById(1L)).thenReturn(of(user))
        `when`(userFindService.findByUsername(userName)).thenReturn(empty())

        assertThatThrownBy({ profileService.viewProfile(1L, userName) }
        ).isInstanceOf(NoSuchElementException::class.java)
    }

    @Test
    fun when_viewProfile_expect_viewer_view_found_user(
        @Mock userName: UserName?,
        @Mock viewer: User, @Mock userToView: User?,
        @Mock profile: Profile?
    ) {
        given(userFindService.findById(1L)).willReturn(of(viewer))
        given(userFindService.findByUsername(userName)).willReturn(of(userToView))
        given(viewer.viewProfile(userToView)).willReturn(profile)

        profileService.viewProfile(1L, userName)

        then(viewer).should(times(1)).viewProfile(userToView)
    }

    @Test
    fun when_viewProfile_with_not_exists_username_expect_NoSuchElementException(@Mock userName: UserName?) {
        `when`(userFindService.findByUsername(userName)).thenReturn(empty())

        assertThatThrownBy({ profileService.viewProfile(userName) }
        ).isInstanceOf(NoSuchElementException::class.java)
    }

    @Test
    fun when_viewProfile_expect_user_getProfile(@Mock userName: UserName?, @Mock user: User, @Mock profile: Profile?) {
        given(userFindService.findByUsername(userName)).willReturn(of(user))
        given(user.getProfile()).willReturn(profile)

        profileService.viewProfile(userName)

        then(user).should(times(1)).getProfile()
    }

    @Test
    fun when_followAndViewProfile_with_not_exists_followeeName_expect_NoSuchElementException(@Mock followeeName: UserName?) {
        `when`(userFindService.findByUsername(followeeName)).thenReturn(empty())

        assertThatThrownBy({ profileService.followAndViewProfile(1L, followeeName) }
        ).isInstanceOf(NoSuchElementException::class.java)
    }

    @Test
    fun when_followAndViewProfile_with_not_exists_followerId_expect_NoSuchElementException(
        @Mock followee: User?,
        @Mock followeeName: UserName?
    ) {
        `when`(userFindService.findByUsername(followeeName)).thenReturn(of(followee))
        `when`(userFindService.findById(anyLong())).thenReturn(empty())

        assertThatThrownBy({ profileService.followAndViewProfile(1L, followeeName) }
        ).isInstanceOf(NoSuchElementException::class.java)
    }

    @Test
    fun when_followAndViewProfile_expect_follower_follows_followee(
        @Mock follower: User, @Mock followeeName: UserName?, @Mock followee: User?, @Mock followeeProfile: Profile?
    ) {
        given(userFindService.findByUsername(followeeName)).willReturn(of(followee))
        given(userFindService.findById(anyLong())).willReturn(of(follower))
        given(follower.followUser(followee)).willReturn(follower)
        given(follower.viewProfile(followee)).willReturn(followeeProfile)

        profileService.followAndViewProfile(1L, followeeName)

        then(follower).should(times(1)).followUser(followee)
    }

    @Test
    fun when_unfollowAndViewProfile_with_not_exists_followeeName_expect_NoSuchElementException(@Mock followeeName: UserName?) {
        `when`(userFindService.findByUsername(followeeName)).thenReturn(empty())

        assertThatThrownBy({ profileService.unfollowAndViewProfile(1L, followeeName) }
        ).isInstanceOf(NoSuchElementException::class.java)
    }

    @Test
    fun when_unfollowAndViewProfile_with_not_exists_followerId_expect_NoSuchElementException(
        @Mock followee: User?,
        @Mock followeeName: UserName?
    ) {
        `when`(userFindService.findByUsername(followeeName)).thenReturn(of(followee))
        `when`(userFindService.findById(anyLong())).thenReturn(empty())

        assertThatThrownBy({ profileService.unfollowAndViewProfile(1L, followeeName) }
        ).isInstanceOf(NoSuchElementException::class.java)
    }

    @Test
    fun when_unfollowAndViewProfile_expect_follower_unfollows_followee(
        @Mock follower: User, @Mock followeeName: UserName?, @Mock followee: User?, @Mock followeeProfile: Profile?
    ) {
        given(userFindService.findByUsername(followeeName)).willReturn(of(followee))
        given(userFindService.findById(anyLong())).willReturn(of(follower))
        given(follower.unfollowUser(followee)).willReturn(follower)
        given(follower.viewProfile(followee)).willReturn(followeeProfile)

        profileService.unfollowAndViewProfile(1L, followeeName)

        then(follower).should(times(1)).unfollowUser(followee)
    }
}