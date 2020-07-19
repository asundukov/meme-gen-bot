package io.cutebot.memegen.service.bot.block

import io.cutebot.telegram.bot.block.BotBlock
import io.cutebot.telegram.bot.block.BotTextBlock
import io.cutebot.telegram.bot.model.TextMessage
import io.cutebot.telegram.interaction.model.ChatAnswer

class AboutBlock: BotTextBlock {

    private val defaultMessage = "Open source project: https://github.com/asundukov/mark-on-image-bot"

    override fun getAnswer(): ChatAnswer {
        return ChatAnswer.text(defaultMessage)
    }

    override fun handleText(message: TextMessage): BotBlock {
        return StartBlock()
    }
}
