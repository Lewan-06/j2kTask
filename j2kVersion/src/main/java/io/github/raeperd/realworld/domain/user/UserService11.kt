package io.github.raeperd.realworld.domain.user

import org.springframework.security.crypto.password.PasswordEncoder

@Service
class UserService(passwordEncoder: PasswordEncoder?, userRepository: UserRepository?) : UserFindService {
    private val passwordEncoder: PasswordEncoder?
    private val userRepository: UserRepository?

    init {
        this.passwordEncoder = passwordEncoder
        this.userRepository = userRepository
    }

    @Transactional
    fun signUp(request: UserSignUpRequest): User? {
        val encodedPassword: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            Password.of(request.getRawPassword(), passwordEncoder)
        return userRepository.save(
            User.of(
                request.getEmail(),
                request.getUserName(),
                encodedPassword
            )
        )
    }

    @Transactional(readOnly = true)
    fun login(email: Email?, rawPassword: String?): Optional<User?>? {
        return userRepository.findFirstByEmail(email)
            .filter({ user -> user.matchesPassword(rawPassword, passwordEncoder) })
    }

    @Override
    @Transactional(readOnly = true)
    fun findById(id: Long): Optional<User?>? {
        return userRepository.findById(id)
    }

    @Override
    fun findByUsername(userName: UserName?): Optional<User?>? {
        return userRepository.findFirstByProfileUserName(userName)
    }

    @Transactional
    fun updateUser(id: Long, request: UserUpdateRequest): User? {
        val user: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            userRepository.findById(id).orElseThrow({ NoSuchElementException() })
        request.getEmailToUpdate()
            .ifPresent(user::changeEmail)
        request.getUserNameToUpdate()
            .ifPresent(user::changeName)
        request.getPasswordToUpdate()
            .map({ rawPassword -> Password.of(rawPassword, passwordEncoder) })
            .ifPresent(user::changePassword)
        request.getImageToUpdate()
            .ifPresent(user::changeImage)
        request.getBioToUpdate()
            .ifPresent(user::changeBio)
        return userRepository.save(user)
    }
}
