package io.cutebot.memegen.service.model

import java.math.BigDecimal

data class Position (
        val top: BigDecimal,
        val bottom: BigDecimal,
        val left: BigDecimal,
        val right: BigDecimal
)
