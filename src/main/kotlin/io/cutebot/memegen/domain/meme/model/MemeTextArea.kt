package io.cutebot.memegen.domain.meme.model

import io.cutebot.memegen.service.model.Color
import io.cutebot.memegen.service.model.Position

data class MemeTextArea (
        val position: Position,
        val textColor: Color,
        val bgColor: Color
)
