package io.cutebot.memegen.service.bot.block

import io.cutebot.telegram.bot.block.BotBlock
import io.cutebot.telegram.bot.block.BotTextBlock
import io.cutebot.telegram.client.model.TgMessage
import io.cutebot.telegram.interaction.model.ChatAnswer
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class HelpBlock(
        @Value("\${help.contact-message}")
        private val helpContactMessage: String
) : BotTextBlock {

    private val defaultMessage =
            """
            /start - begin using bot\n" +
            "/help - summon this message\n" +
            "/marks - show all marks\n" +
            "/about - show about info\n" +
            Contact: $helpContactMessage + "\n" +
            """

    override fun getAnswer(): ChatAnswer {
        return ChatAnswer.text(defaultMessage)
    }

    override fun handleText(message: TgMessage): BotBlock {
        return StartBlock()
    }

}
