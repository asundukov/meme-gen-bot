package io.cutebot.markonimage.service.exception

import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(NOT_FOUND)
class BotNotFoundException: RuntimeException {
    constructor(id: Int): super("Bot {$id} not found")
    constructor(token: String): super("Bot" + token.substring(15) + " not found")
}