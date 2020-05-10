package io.cutebot.markonimage.domain.mark.model

import io.cutebot.imagegenerator.MarkPosition
import java.io.InputStream
import java.math.BigDecimal

class NewMark(
        override val botId: Int,
        override val position: MarkPosition,
        override val sizePercent: BigDecimal,
        val image: InputStream
) : Mark
