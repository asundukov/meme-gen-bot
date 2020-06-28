package io.cutebot.markonimage.domain.mark.model

import io.cutebot.imagegenerator.MarkPosition

class ExistedMark(entity: MarkEntity) {
    val id = entity.markId
    val totalImages = entity.totalImages
    val createdOn = entity.createdOn
    val botId = entity.bot.botId
    val position = MarkPosition.typeById(entity.position)
    val sizeValue = entity.sizeValue
    val title = entity.title
    val description = entity.description
    val opacity = entity.opacity
}
