package io.github.raeperd.realworld.application.security

import io.github.raeperd.realworld.domain.jwt.JWTDeserializer

@EnableConfigurationProperties(SecurityConfigurationProperties::class)
@Configuration
class SecurityConfiguration internal constructor(private val properties: SecurityConfigurationProperties?) :
    WebSecurityConfigurerAdapter(), WebMvcConfigurer {
    @Override
    fun configure(web: WebSecurity) {
        web.ignoring().antMatchers(POST, "/users", "/users/login")
    }

    @Override
    @Throws(Exception::class)
    protected fun configure(http: HttpSecurity) {
        http.csrf().disable()
        http.cors()
        http.formLogin().disable()
        http.logout().disable()
        http.addFilterBefore(JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
        http.authorizeRequests()
            .antMatchers(GET, "/profiles/*").permitAll()
            .antMatchers(GET, "/articles/**").permitAll()
            .antMatchers(GET, "/tags/**").permitAll()
            .anyRequest().authenticated()
    }

    @Bean
    fun jwtAuthenticationProvider(jwtDeserializer: JWTDeserializer?): JWTAuthenticationProvider? {
        return JWTAuthenticationProvider(jwtDeserializer)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }

    @Override
    fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedMethods("GET", "HEAD", "POST", "DELETE", "PUT")
            .allowedOrigins(properties.getAllowedOrigins().toArray(arrayOfNulls<String>(0)))
            .allowedHeaders("*")
            .allowCredentials(true)
    }
}

@ConstructorBinding
@ConfigurationProperties("security")
internal class SecurityConfigurationProperties(private val allowedOrigins: List<String?>?) {
    fun getAllowedOrigins(): List<String?>? {
        return allowedOrigins
    }
}