package io.cutebot.memegen.service.bot

import io.cutebot.memegen.service.bot.block.StartBlock
import io.cutebot.telegram.bot.block.BotBlock
import io.cutebot.telegram.bot.command.Command
import io.cutebot.telegram.bot.model.RawMessage

class StartCommand: Command {
    override fun getCommand(): String = "/start"

    override fun getCommandDescription(): String = "Restart bot"

    override fun handleCommand(query: String, message: RawMessage): BotBlock = StartBlock()

    override fun isSystemCommand(): Boolean = true
}
