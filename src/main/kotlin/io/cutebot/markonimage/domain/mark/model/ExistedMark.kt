package io.cutebot.markonimage.domain.mark.model

import io.cutebot.imagegenerator.MarkPosition

class ExistedMark(entity: MarkEntity): Mark {
    val id = entity.markId
    var totalImages = entity.totalImages
    val createdOn = entity.createdOn
    override val botId = entity.bot.botId
    override var position = MarkPosition.typeById(entity.position)
    override var sizePercent = entity.sizePercent
}
