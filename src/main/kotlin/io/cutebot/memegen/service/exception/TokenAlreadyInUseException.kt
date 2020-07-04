package io.cutebot.memegen.service.exception

import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(BAD_REQUEST)
class TokenAlreadyInUseException : RuntimeException("Given token already in use")
