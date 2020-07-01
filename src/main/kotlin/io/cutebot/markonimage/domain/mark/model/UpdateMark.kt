package io.cutebot.markonimage.domain.mark.model

import io.cutebot.imagegenerator.MarkPosition
import java.math.BigDecimal

data class UpdateMark (
        val position: MarkPosition,
        val sizeValue: BigDecimal,
        val title: String,
        val description: String,
        val opacity: BigDecimal
)
