package io.cutebot.memegen.web.model

import io.cutebot.memegen.domain.meme.model.ExistedMeme

class GetMemeResponse(meme: ExistedMeme) {
    val id = meme.id
    val botId = meme.botId
    val createdOn = meme.createdOn
    val totalImages = meme.totalImages
    val alias = meme.alias
}
