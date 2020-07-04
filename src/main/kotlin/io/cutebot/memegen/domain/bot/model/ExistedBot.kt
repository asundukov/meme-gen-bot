package io.cutebot.memegen.domain.bot.model

data class ExistedBot(val entity: BotEntity) {
    val id = entity.botId
    val token = entity.token
    val adminUsrId = entity.adminUsrId
    val title = entity.title
    val username = entity.username
    val createdOn = entity.createdOn
    val totalGenerated = entity.totalGenerated
}
