package io.cutebot.markonimage.domain.bot.model

data class ExistedBot(val entity: BotEntity): Bot {
    val id = entity.botId
    override val token = entity.token
    override val adminUsrId = entity.adminUsrId
    val createdOn = entity.createdOn
    val totalImages = entity.totalImages
}
