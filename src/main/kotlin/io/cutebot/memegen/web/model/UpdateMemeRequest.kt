package io.cutebot.memegen.web.model

import io.cutebot.memegen.domain.meme.model.UpdateMeme

open class UpdateMemeRequest(
        val alias: String,
        val textAreas: TextAreas
) {

    fun getUpdateModel(): UpdateMeme {
        return UpdateMeme(
                alias = alias,
                textAreas = textAreas.getModel()
        )
    }

}
