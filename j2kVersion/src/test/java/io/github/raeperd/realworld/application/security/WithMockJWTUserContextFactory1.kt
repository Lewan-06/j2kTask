package io.github.raeperd.realworld.application.security

import io.github.raeperd.realworld.infrastructure.jwt.UserJWTPayload

internal class WithMockJWTUserContextFactory : WithSecurityContextFactory<WithMockJWTUser?> {
    @Override
    fun createSecurityContext(annotation: WithMockJWTUser?): SecurityContext? {
        val context: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            SecurityContextHolder.createEmptyContext()
        context.setAuthentication(MockJWTAuthentication(mock(UserJWTPayload::class.java)))
        return context
    }

    private class MockJWTAuthentication(jwtPayload: UserJWTPayload?) :
        AbstractAuthenticationToken(singleton(SimpleGrantedAuthority("USER"))) {
        private val jwtPayload: UserJWTPayload?

        init {
            super.setAuthenticated(true)
            this.jwtPayload = jwtPayload
        }

        @Override
        fun getPrincipal(): Object? {
            return jwtPayload
        }

        @Override
        fun getCredentials(): Object? {
            return "MOCKED CREDENTIAL"
        }
    }
}
