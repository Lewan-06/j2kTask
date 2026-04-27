package io.github.raeperd.realworld.domain.user

import org.springframework.stereotype.Service

@Service
class ProfileService internal constructor(userFindService: UserFindService?) {
    private val userFindService: UserFindService?

    init {
        this.userFindService = userFindService
    }

    @Transactional(readOnly = true)
    fun viewProfile(viewerId: Long, usernameToView: UserName?): Profile? {
        val viewer: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            userFindService.findById(viewerId).orElseThrow({ NoSuchElementException() })
        return userFindService.findByUsername(usernameToView)
            .map(viewer::viewProfile)
            .orElseThrow({ NoSuchElementException() })
    }

    @Transactional(readOnly = true)
    fun viewProfile(userName: UserName?): Profile? {
        return userFindService.findByUsername(userName)
            .map(User::getProfile)
            .orElseThrow({ NoSuchElementException() })
    }

    @Transactional
    fun followAndViewProfile(followerId: Long, followeeUserName: UserName?): Profile? {
        val followee: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            userFindService.findByUsername(followeeUserName).orElseThrow({ NoSuchElementException() })
        return userFindService.findById(followerId)
            .map({ follower -> follower.followUser(followee) })
            .map({ follower -> follower.viewProfile(followee) })
            .orElseThrow({ NoSuchElementException() })
    }

    @Transactional
    fun unfollowAndViewProfile(followerId: Long, followeeUserName: UserName?): Profile? {
        val followee: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            userFindService.findByUsername(followeeUserName).orElseThrow({ NoSuchElementException() })
        return userFindService.findById(followerId)
            .map({ follower -> follower.unfollowUser(followee) })
            .map({ follower -> follower.viewProfile(followee) })
            .orElseThrow({ NoSuchElementException() })
    }
}
