package io.cutebot.memegen.web.model

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.cutebot.memegen.domain.meme.model.NewMeme
import org.springframework.web.multipart.MultipartFile
import javax.validation.constraints.Min

class CreateMemeRequest(
        @field: Min(1)
        val botId: Int,

        var image: MultipartFile?,

        alias: String,

        textAreas: String

): UpdateMemeRequest(alias, mapper.readValue(textAreas, TextAreas::class.java)) {

    fun getCreateModel(): NewMeme {
        image ?: error("image didn't located")

        return NewMeme(
                botId = botId,
                image = image?.inputStream!!,
                alias = alias,
                textAreas = textAreas.getModel()
        )
    }

    companion object {
        private val mapper = jacksonObjectMapper()
    }

}
