package io.github.raeperd.realworld.application.security

import org.springframework.security.authentication.AbstractAuthenticationToken

internal class JWTAuthenticationFilter : OncePerRequestFilter() {
    @Override
    @Throws(ServletException::class, IOException::class)
    protected fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse?,
        filterChain: FilterChain
    ) {
        ofNullable(request.getHeader(AUTHORIZATION))
            .map({ authHeader -> authHeader.substring("Token ".length()) })
            .map({ token: String? -> JWT(token) })
            .ifPresent(getContext()::setAuthentication)
        filterChain.doFilter(request, response)
    }

    @SuppressWarnings("java:S2160")
    internal class JWT private constructor(private val token: String?) : AbstractAuthenticationToken(null) {
        @Override
        fun getPrincipal(): Object? {
            return token
        }

        @Override
        fun getCredentials(): Object? {
            return null
        }
    }
}
