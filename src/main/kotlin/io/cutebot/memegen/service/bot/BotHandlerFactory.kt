package io.cutebot.memegen.service.bot

import io.cutebot.memegen.domain.BaseBot
import io.cutebot.memegen.domain.bot.model.ExistedBot
import io.cutebot.memegen.service.bot.block.AboutBlock
import io.cutebot.memegen.service.bot.block.HelpBlock
import io.cutebot.memegen.service.bot.block.StartBlock
import io.cutebot.telegram.bot.Bot
import io.cutebot.telegram.bot.command.RedirectCommand
import org.springframework.stereotype.Service

@Service
class BotHandlerFactory(
        private val inlineHandler: InlineHandler,
        private val helpBlock: HelpBlock
) {
    private val startBlock = StartBlock()
    private val aboutBlock = AboutBlock()

    fun generateBotHandler(existedBot: ExistedBot): Bot {

        val commands = listOf(
                StartCommand(),
                RedirectCommand(helpBlock, "/help", true, "Help info"),
                RedirectCommand(aboutBlock, "/about", true, "About bot and contacts")
        )

        return GenMemeBotHandler(
                baseBot = BaseBot(existedBot.id, existedBot.token),
                inlineHandler = inlineHandler,
                commandBlocks = commands,
                currentBlock = startBlock
        )
    }

}
