package io.cutebot.memegen.domain.meme.model

data class UpdateMeme (
        val alias: String,
        val textAreas: List<MemeTextArea>
)
