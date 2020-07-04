package io.cutebot.memegen.service.messagehandlers

import io.cutebot.telegram.handlers.BaseBot
import io.cutebot.telegram.tgmodel.TgUser

class StartMessageHandler : MessageHandler {

    private val defaultMessage = "Welcome to mark-on-image-bot. Send me image to create new image with mark.\n" +
            "/makeavatar to create your avatar with selected mark\n" +
            "/marks to show all possible marks\n" +
            "/help to show more possible commands"

    override fun handle(bot: BaseBot, params: String, chatId: Long, user: TgUser): String {
        return defaultMessage
    }

    override fun getCommandDescription(bot: BaseBot): String
            = "Restart bot"
}
