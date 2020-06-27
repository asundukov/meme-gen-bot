package io.cutebot.markonimage.service.messagehandlers

import io.cutebot.telegram.handlers.BaseBot
import io.cutebot.telegram.tgmodel.TgUser

class AboutMessageHandler : MessageHandler {

    private val defaultMessage = "Open source project: https://github.com/asundukov/mark-on-image-bot"

    override fun handle(bot: BaseBot, params: String, chatId: Long, user: TgUser): String {
        return defaultMessage
    }

    override fun getCommandDescription(bot: BaseBot): String
            = "Show about page"
}
