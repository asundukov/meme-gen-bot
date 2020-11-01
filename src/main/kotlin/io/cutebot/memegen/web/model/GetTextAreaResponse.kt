package io.cutebot.memegen.web.model

import io.cutebot.memegen.domain.meme.model.ExistedArea

class GetTextAreaResponse(area: ExistedArea) {
    val position = area.position
    val textColor = ColorDto.fromColor(area.textColor)
    val bgColor = ColorDto.fromColor(area.bgColor)
}
