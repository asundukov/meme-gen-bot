package io.cutebot.markonimage.web.model

import io.cutebot.markonimage.domain.mark.model.ExistedMark

class GetMarkResponse(mark: ExistedMark) {
    val id = mark.id
    val botId = mark.botId
    val createdOn = mark.createdOn
    val totalImages = mark.totalImages
    val sizeValue = mark.sizeValue
    val title = mark.title
    val description = mark.description
}
