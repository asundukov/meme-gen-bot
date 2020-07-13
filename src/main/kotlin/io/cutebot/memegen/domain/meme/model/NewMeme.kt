package io.cutebot.memegen.domain.meme.model

import java.io.InputStream

class NewMeme(
        val botId: Int,
        val image: InputStream,
        val alias: String,
        val textAreas: List<MemeTextArea>
)
