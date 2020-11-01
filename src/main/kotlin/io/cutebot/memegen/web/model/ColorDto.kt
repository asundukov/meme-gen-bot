package io.cutebot.memegen.web.model

import com.fasterxml.jackson.annotation.JsonIgnore
import io.cutebot.memegen.service.model.Color
import javax.validation.constraints.Max
import javax.validation.constraints.Min

class ColorDto (
        @field: Min(0)
        @field: Max(255)
        val red: Int,

        @field: Min(0)
        @field: Max(255)
        val green: Int,

        @field: Min(0)
        @field: Max(255)
        val blue: Int,

        @field: Min(0)
        @field: Max(255)
        val alpha: Int
) {
    @JsonIgnore
    fun toColor(): Color {
        return Color(red, green, blue, alpha)
    }
    companion object {
        fun fromColor(color: Color): ColorDto {
            return ColorDto(color.red, color.green, color.blue, color.alpha)
        }

    }
}
