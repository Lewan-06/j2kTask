package io.github.raeperd.realworld.application.security

import io.github.raeperd.realworld.domain.jwt.JWTDeserializer

internal class JWTAuthenticationProvider(jwtDeserializer: JWTDeserializer?) : AuthenticationProvider {
    private val jwtDeserializer: JWTDeserializer?

    init {
        this.jwtDeserializer = jwtDeserializer
    }

    @Override
    @Throws(AuthenticationException::class)
    fun authenticate(authentication: Authentication?): Authentication? {
        return of(authentication).map(JWT::class.java::cast)
            .map(JWTAuthenticationFilter.JWT::getPrincipal)
            .map(Object::toString)
            .map({ token -> JWTAuthentication(token, jwtDeserializer.jwtPayloadFromJWT(token)) })
            .orElseThrow({ IllegalStateException() })
    }

    @Override
    fun supports(authentication: Class<*>?): Boolean {
        return JWT::class.java.isAssignableFrom(authentication)
    }

    @SuppressWarnings("java:S2160")
    private class JWTAuthentication(token: String?, jwtPayload: JWTPayload?) :
        AbstractAuthenticationToken(singleton(SimpleGrantedAuthority("USER"))) {
        private val jwtPayload: JWTPayload?
        private val token: String?

        init {
            super.setAuthenticated(true)
            this.jwtPayload = jwtPayload
            this.token = token
        }

        @Override
        fun getPrincipal(): Object? {
            return jwtPayload
        }

        @Override
        fun getCredentials(): Object? {
            return token
        }
    }
}
