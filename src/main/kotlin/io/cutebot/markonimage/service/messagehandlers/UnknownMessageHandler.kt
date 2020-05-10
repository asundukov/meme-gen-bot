package io.cutebot.markonimage.service.messagehandlers

import io.cutebot.telegram.handlers.BaseBot
import io.cutebot.telegram.tgmodel.TgUser

class UnknownMessageHandler : MessageHandler {

    private val defaultMessage = "Type /help to see possible bot commands"

    override fun handle(bot: BaseBot, params: String, chatId: Long, user: TgUser): String {
        return defaultMessage
    }
}