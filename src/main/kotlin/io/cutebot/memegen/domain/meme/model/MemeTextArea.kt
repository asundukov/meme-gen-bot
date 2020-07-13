package io.cutebot.memegen.domain.meme.model

import java.math.BigDecimal

data class MemeTextArea (
        val top: BigDecimal,
        val bottom: BigDecimal,
        val left: BigDecimal,
        val right: BigDecimal
)
