package io.cutebot.memegen.domain.meme.model

import java.io.InputStream

class NewMeme(
        val botId: Int,
        val image: InputStream,
        alias: String,
        textAreas: List<MemeTextArea>
): UpdateMeme(alias, textAreas)
