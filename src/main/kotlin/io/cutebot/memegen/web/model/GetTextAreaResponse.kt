package io.cutebot.memegen.web.model

import io.cutebot.memegen.domain.meme.model.ExistedArea

class GetTextAreaResponse(area: ExistedArea) {
    val left = area.left
    val top = area.top
    val right = area.right
    val bottom = area.bottom
}
