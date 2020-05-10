package io.cutebot.markonimage.domain.mark.model

import io.cutebot.imagegenerator.MarkPosition
import java.math.BigDecimal

interface Mark {
    val botId: Int
    val position: MarkPosition
    val sizePercent: BigDecimal
}
