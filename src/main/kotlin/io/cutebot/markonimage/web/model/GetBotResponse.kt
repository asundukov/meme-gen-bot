package io.cutebot.markonimage.web.model

import io.cutebot.markonimage.domain.bot.model.ExistedBot

class GetBotResponse(bot: ExistedBot) {
    val botId = bot.id
    val totalImages = bot.totalImages
    val createdOn = bot.createdOn
    val title = bot.title
    val adminUsrId = bot.adminUsrId
}
