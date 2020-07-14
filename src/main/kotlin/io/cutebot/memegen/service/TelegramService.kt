package io.cutebot.memegen.service

import io.cutebot.telegram.client.TelegramApi
import org.springframework.stereotype.Service

@Service
class TelegramService {
    val api = TelegramApi()
}