package io.cutebot.memegen.service.bot

import io.cutebot.memegen.domain.BaseBot
import io.cutebot.memegen.domain.bot.model.ExistedBot
import io.cutebot.memegen.service.bot.block.AboutBlock
import io.cutebot.memegen.service.bot.block.HelpBlock
import io.cutebot.memegen.service.bot.block.StartBlock
import io.cutebot.telegram.bot.Bot
import io.cutebot.telegram.bot.block.CommandBlock
import io.cutebot.telegram.bot.block.RedirectCommandBlock
import org.springframework.stereotype.Service

@Service
class BotHandlerFactory(
        private val inlineHandler: InlineHandler,
        private val helpBlock: HelpBlock
) {
    private val startBlock = StartBlock()
    private val aboutBlock = AboutBlock()

    fun generateBotHandler(existedBot: ExistedBot): Bot {

        val commands: Map<String, CommandBlock> = mapOf(
                "/start" to RedirectCommandBlock(startBlock),
                "/help" to RedirectCommandBlock(helpBlock),
                "/about" to RedirectCommandBlock(aboutBlock)
        )

        return GenMemeBotHandler(
                baseBot = BaseBot(existedBot.id, existedBot.token),
                inlineHandler = inlineHandler,
                commandBlocks = commands,
                currentBlock = startBlock
        )
    }

//    fun setCommands(bot: BaseBot) {
//        val tgBotCommands = TgBotCommands(ArrayList())
//        messagesMap.forEach {
//            val description = it.value.getCommandDescription(bot)
//            if (description != null) {
//                tgBotCommands.commands.add(TgBotCommand(it.key, description))
//            }
//        }
//        telegramService.setCommands(bot.token, tgBotCommands)
//    }

}
