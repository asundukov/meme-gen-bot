package io.cutebot.memegen.service.bot.block

import io.cutebot.telegram.bot.block.BotBlock
import io.cutebot.telegram.bot.block.BotTextBlock
import io.cutebot.telegram.client.model.TgMessage
import io.cutebot.telegram.interaction.model.ChatAnswer


class StartBlock : BotTextBlock {

    private val defaultMessage =
            """
            Welcome to meme-gen-bot
            You can easy create some funny memes in inline mode
            """

   override fun getAnswer(): ChatAnswer {
        return ChatAnswer.text(defaultMessage)
    }

    override fun handleText(message: TgMessage): BotBlock {
        return this
    }
}
