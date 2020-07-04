package io.cutebot.memegen.domain.meme.model

class ExistedMeme(entity: MemeEntity) {
    val id = entity.memeId
    val totalImages = entity.totalGenerated
    val createdOn = entity.createdOn
    val botId = entity.bot.botId
    val title = entity.title
    val description = entity.description
}
