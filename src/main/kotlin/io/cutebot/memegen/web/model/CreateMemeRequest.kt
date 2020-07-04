package io.cutebot.memegen.web.model

import io.cutebot.imagegenerator.MarkPosition
import io.cutebot.memegen.domain.meme.model.NewMeme
import org.springframework.web.multipart.MultipartFile
import java.math.BigDecimal
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.Min

class CreateMemeRequest(
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
    fun getCreateModel(): NewMeme {
        image ?: error("image didn't located")
        return NewMeme(
                botId = botId,
                image = image?.inputStream!!,
                title = title,
                description = description
        )
    }
}
