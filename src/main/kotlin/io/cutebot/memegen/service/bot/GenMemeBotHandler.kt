package io.cutebot.memegen.service.bot

import io.cutebot.memegen.domain.BaseBot
import io.cutebot.telegram.bot.CommandsStatefulBot
import io.cutebot.telegram.bot.block.BotBlock
import io.cutebot.telegram.bot.command.Command
import io.cutebot.telegram.client.model.inline.TgAnswerInlineQuery
import io.cutebot.telegram.client.model.inline.TgInlineQuery

class GenMemeBotHandler(
        private val baseBot: BaseBot,
        currentBlock: BotBlock,
        commandBlocks: List<Command>,
        private val inlineHandler: InlineHandler
): CommandsStatefulBot(currentBlock, commandBlocks) {

    override fun getToken(): String {
        return baseBot.token
    }

    override fun handleInlineQuery(inlineQuery: TgInlineQuery): TgAnswerInlineQuery {
        return inlineHandler.handle(baseBot.id, inlineQuery)
    }

}
