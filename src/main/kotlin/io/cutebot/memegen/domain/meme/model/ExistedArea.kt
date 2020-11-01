package io.cutebot.memegen.domain.meme.model

import io.cutebot.memegen.service.model.Color
import io.cutebot.memegen.service.model.Position

class ExistedArea(entity: MemeTextAreaEntity) {
    val position = Position(entity.topPos, entity.bottomPos, entity.leftPos, entity.rightPos)
    val textColor = Color(entity.textColorRed, entity.textColorGreen, entity.textColorBlue, entity.textColorAlpha)
    val bgColor = Color(entity.bgColorRed, entity.bgColorGreen, entity.bgColorBlue, entity.bgColorAlpha)
}
