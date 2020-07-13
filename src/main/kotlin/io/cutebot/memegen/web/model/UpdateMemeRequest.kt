package io.cutebot.memegen.web.model

import io.cutebot.memegen.domain.meme.model.UpdateMeme

class UpdateMemeRequest(
        val alias: String
) {
    fun getUpdateModel(): UpdateMeme {
        return UpdateMeme(
                alias = alias
        )
    }
}
