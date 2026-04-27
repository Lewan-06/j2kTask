package io.github.raeperd.realworld.application

import org.springframework.context.annotation.Bean

@Configuration
internal class WebMvcConfiguration {
    @Bean
    fun pageableHandlerMethodArgumentResolverCustomizer(): PageableHandlerMethodArgumentResolverCustomizer? {
        return PageableHandlerMethodArgumentResolverCustomizer { pageableResolver ->
            pageableResolver.setSizeParameterName("limit")
            pageableResolver.setPageParameterName("offset")
        }
    }
}
