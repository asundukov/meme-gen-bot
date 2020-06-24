package io.cutebot.markonimage.web

import io.cutebot.markonimage.web.exception.ForbiddenWebException
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class TokenInterceptor(
        private val token: String
) : HandlerInterceptor {

    private val tokenHeaderName = "X-Access-Token"

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val actualToken = request.getHeader(tokenHeaderName) ?: throw ForbiddenWebException()

        if (actualToken != token) {
            throw ForbiddenWebException()
        }

        return true
    }
}
