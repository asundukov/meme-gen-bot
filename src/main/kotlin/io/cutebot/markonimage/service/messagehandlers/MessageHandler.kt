package io.cutebot.markonimage.service.messagehandlers

import io.cutebot.telegram.handlers.BaseBot
import io.cutebot.telegram.tgmodel.TgUser

interface MessageHandler {
    fun handle(bot: BaseBot, params: String, chatId: Long, user: TgUser): String

    fun getCommandDescription(bot: BaseBot): String?
}
