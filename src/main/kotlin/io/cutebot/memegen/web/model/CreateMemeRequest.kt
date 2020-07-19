package io.cutebot.memegen.web.model

import io.cutebot.memegen.domain.meme.model.NewMeme
import org.springframework.web.multipart.MultipartFile
import javax.validation.constraints.Min

class CreateMemeRequest(
        @field: Min(1)
        val botId: Int,

        var image: MultipartFile?,

        alias: String,

        textAreaCoords: String

): UpdateMemeRequest(alias, textAreaCoords) {

    fun getCreateModel(): NewMeme {
        image ?: error("image didn't located")

        return NewMeme(
                botId = botId,
                image = image?.inputStream!!,
                alias = alias,
                textAreas = areaSquares
        )
    }

}
