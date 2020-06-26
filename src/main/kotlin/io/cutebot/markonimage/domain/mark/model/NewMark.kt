package io.cutebot.markonimage.domain.mark.model

import io.cutebot.imagegenerator.MarkPosition
import java.io.InputStream
import java.math.BigDecimal

class NewMark(
        val botId: Int,
        val position: MarkPosition,
        val sizeValue: BigDecimal,
        val image: InputStream,
        val title: String,
        val description: String
)
