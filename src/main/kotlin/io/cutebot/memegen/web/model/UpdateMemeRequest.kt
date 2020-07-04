package io.cutebot.memegen.web.model

import io.cutebot.memegen.domain.meme.model.UpdateMeme

class UpdateMemeRequest(
        val title: String,
        val description: String
) {
    fun getUpdateModel(): UpdateMeme {
        return UpdateMeme(
                title = title,
                description = description
        )
    }
}
