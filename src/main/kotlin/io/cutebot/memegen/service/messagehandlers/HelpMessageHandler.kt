package io.cutebot.memegen.service.messagehandlers

import io.cutebot.telegram.handlers.BaseBot
import io.cutebot.telegram.tgmodel.TgUser
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class HelpMessageHandler(
        @Value("\${help.contact-message}")
        private val helpContactMessage: String
) : MessageHandler {

    private val defaultMessage = "/start - begin using bot\n" +
            "/help - summon this message\n" +
            "/marks - show all marks\n" +
            "/about - show about info\n" +
            helpContactMessage + "\n" +
            ""

    override fun handle(bot: BaseBot, params: String, chatId: Long, user: TgUser): String {
        return defaultMessage
    }

    override fun getCommandDescription(bot: BaseBot): String
            = "Show help info"
}
