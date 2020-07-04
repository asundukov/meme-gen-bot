package io.cutebot.memegen.web.model

import io.cutebot.memegen.domain.bot.model.ExistedBot

class GetBotResponse(bot: ExistedBot) {
    val botId = bot.id
    val totalGenerated = bot.totalGenerated
    val createdOn = bot.createdOn
    val title = bot.title
    val adminUsrId = bot.adminUsrId
    val username = bot.username
}
