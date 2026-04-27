package io.github.raeperd.realworld.application.security

import org.springframework.security.test.context.support.WithSecurityContext

@Retention(RUNTIME)
@WithSecurityContext(factory = WithMockJWTUserContextFactory::class)
annotation class WithMockJWTUser(val userId: Long = 0L)
