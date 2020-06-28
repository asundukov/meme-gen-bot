package io.cutebot.markonimage.web.model

import io.cutebot.imagegenerator.MarkPosition
import io.cutebot.markonimage.domain.mark.model.NewMark
import org.springframework.web.multipart.MultipartFile
import java.math.BigDecimal
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.Min

class CreateMarkRequest(
        @field: Min(1)
        val botId: Int,

        val position: MarkPosition,

        @field: DecimalMin("0.01")
        val sizeValue: BigDecimal,

        var image: MultipartFile?,

        val title: String,

        val description: String,

        val opacity: BigDecimal

) {
    fun getCreateModel(): NewMark {
        image ?: error("image didn't located")
        return NewMark(
                botId = botId,
                position = position,
                sizeValue = sizeValue,
                image = image?.inputStream!!,
                title = title,
                description = description,
                opacity = opacity
        )
    }
}
