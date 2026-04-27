package io.github.raeperd.realworld.domain.user

import org.junit.jupiter.api.BeforeEach

@ExtendWith(MockitoExtension::class)
internal class UserServiceTest {
    private var userService: UserService? = null

    @Mock
    private val passwordEncoder: PasswordEncoder? = null

    @Mock
    private val userRepository: UserRepository? = null

    @BeforeEach
    fun initializeUserService() {
        this.userService = UserService(passwordEncoder, userRepository)
    }

    @Test
    fun when_signUp_expect_password_encoded(@Mock request: UserSignUpRequest) {
        given(request.getRawPassword()).willReturn("raw-password")

        userService.signUp(request)

        then(passwordEncoder).should(times(1)).encode("raw-password")
    }

    @Test
    fun when_login_expect_user_matches_password(@Mock email: Email?, @Mock user: User?) {
        given(userRepository.findFirstByEmail(email)).willReturn(of(user))

        userService.login(email, "raw-password")

        then(user).should(times(1)).matchesPassword("raw-password", passwordEncoder)
    }

    @Test
    fun when_findById_expect_repository_findById() {
        userService.findById(1L)

        verify(userRepository, times(1)).findById(1L)
    }

    @Test
    fun when_findByUsername_expect_repository_findFirstByProfileUserName(@Mock userName: UserName?) {
        userService.findByUsername(userName)

        verify(userRepository, times(1)).findFirstByProfileUserName(userName)
    }

    @Test
    fun when_updateUser_with_invalid_id_expect_NoSuchElementException(@Mock request: UserUpdateRequest?) {
        `when`(userRepository.findById(1L)).thenReturn(empty())

        assertThatThrownBy(
            { userService.updateUser(1L, request) }
        ).isInstanceOf(NoSuchElementException::class.java)
    }

    @Test
    fun when_updateUser_expect_userRepository_save(@Mock user: User?, @Mock request: UserUpdateRequest?) {
        given(userRepository.findById(1L)).willReturn(of(user))

        userService.updateUser(1L, request)

        then(userRepository).should(times(1)).save(user)
    }

    @Test
    fun when_updateUser_email_expect_user_changEmail(
        @Mock user: User?,
        @Mock request: UserUpdateRequest,
        @Mock email: Email?
    ) {
        given(userRepository.findById(1L)).willReturn(of(user))
        given(request.getEmailToUpdate()).willReturn(of(email))

        userService.updateUser(1L, request)

        then(user).should(times(1)).changeEmail(email)
        verifyNoMoreInteractions(user)
    }

    @Test
    fun when_updateUser_name_expect_user_changeName(
        @Mock user: User?,
        @Mock request: UserUpdateRequest,
        @Mock userName: UserName?
    ) {
        given(userRepository.findById(1L)).willReturn(of(user))
        given(request.getUserNameToUpdate()).willReturn(of(userName))

        userService.updateUser(1L, request)

        then(user).should(times(1)).changeName(userName)
        verifyNoMoreInteractions(user)
    }

    @Test
    fun when_updateUser_password_expect_passwordEncoder_encode_password(
        @Mock user: User?,
        @Mock request: UserUpdateRequest
    ) {
        given(userRepository.findById(1L)).willReturn(of(user))
        given(request.getPasswordToUpdate()).willReturn(of("new-password"))

        userService.updateUser(1L, request)

        then(passwordEncoder).should(times(1)).encode("new-password")
    }

    @Test
    fun when_updateUser_password_expect_user_changesPassword_encoded_password(
        @Mock user: User?,
        @Mock request: UserUpdateRequest,
        @Mock password: Password?
    ) {
        given(userRepository.findById(1L)).willReturn(of(user))
        given(request.getPasswordToUpdate()).willReturn(of("new-password"))


        mockStatic(Password::class.java).use { mockStatic ->
            mockStatic.`when`({ Password.of("new-password", passwordEncoder) }).thenReturn(password)
            userService.updateUser(1L, request)
        }
        then(user).should(times(1)).changePassword(password)
        verifyNoMoreInteractions(user)
    }

    @Test
    fun when_update_image_expect_user_changeImage(
        @Mock user: User?,
        @Mock request: UserUpdateRequest,
        @Mock image: Image?
    ) {
        given(userRepository.findById(1L)).willReturn(of(user))
        given(request.getImageToUpdate()).willReturn(of(image))

        userService.updateUser(1L, request)

        then(user).should(times(1)).changeImage(image)
        verifyNoMoreInteractions(user)
    }

    @Test
    fun when_update_bio_expect_user_changes_bio(@Mock user: User?, @Mock request: UserUpdateRequest) {
        given(userRepository.findById(1L)).willReturn(of(user))
        given(request.getBioToUpdate()).willReturn(of("new-bio"))

        userService.updateUser(1L, request)

        then(user).should(times(1)).changeBio("new-bio")
        verifyNoMoreInteractions(user)
    }
}