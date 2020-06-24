package io.cutebot

import io.cutebot.markonimage.web.TokenInterceptor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class WebConfiguration(
        @Value("\${access.token}")
        private val token: String
): WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(TokenInterceptor(token))
    }
}
