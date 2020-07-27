package io.cutebot.memegen.domain.meme.model

class ExistedMeme(entity: MemeEntity) {
    val id = entity.memeId
    val totalImages = entity.totalGenerated
    val createdOn = entity.createdOn
    val botId = entity.bot.botId
    val alias = entity.alias
    val areas = entity.areas.sortedBy { it.num } .map { ExistedArea(it) }

    val width = entity.width
    val height = entity.height
    val thumbWidth = entity.thumbWidth
    val thumbHeight = entity.thumbHeight
}
