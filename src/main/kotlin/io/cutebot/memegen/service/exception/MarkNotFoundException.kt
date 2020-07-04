package io.cutebot.memegen.service.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class MarkNotFoundException(id: Int)
    : RuntimeException("Mark {$id} not found")
