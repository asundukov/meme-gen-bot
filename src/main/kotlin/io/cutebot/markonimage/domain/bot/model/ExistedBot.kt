package io.cutebot.markonimage.domain.bot.model

data class ExistedBot(val entity: BotEntity) {
    val id = entity.botId
    val token = entity.token
    val adminUsrId = entity.adminUsrId
    val title = entity.title
    val createdOn = entity.createdOn
    val totalImages = entity.totalImages
}
