package io.cutebot.memegen.service.bot

import io.cutebot.telegram.bot.block.BotBlock

abstract class MemeGenCommandBlock(
        val botId: Int
): BotBlock
