package io.cutebot.memegen.domain.meme.model

import java.awt.Color

class ExistedArea(entity: MemeTextAreaEntity) {
    val top = entity.topPos
    val bottom = entity.bottomPos
    val left = entity.leftPos
    val right = entity.rightPos

    val textColor = Color(entity.textColorRed, entity.textColorGreen, entity.textColorBlue, entity.textColorAlpha)
    val bgColor = Color(entity.bgColorRed, entity.bgColorGreen, entity.bgColorBlue, entity.bgColorAlpha)
}
