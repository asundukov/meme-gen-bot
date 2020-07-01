package io.cutebot.markonimage.web.model

import io.cutebot.imagegenerator.MarkPosition
import io.cutebot.markonimage.domain.mark.model.NewMark
import io.cutebot.markonimage.domain.mark.model.UpdateMark
import org.springframework.web.multipart.MultipartFile
import java.math.BigDecimal
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.Min

class UpdateMarkRequest(
        val position: MarkPosition,

        @field: DecimalMin("0.01")
        val sizeValue: BigDecimal,

        val title: String,

        val description: String,

        val opacity: BigDecimal

) {
    fun getUpdateModel(): UpdateMark {
        return UpdateMark(
                position = position,
                sizeValue = sizeValue,
                title = title,
                description = description,
                opacity = opacity
        )
    }
}
