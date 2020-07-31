package io.cutebot.memegen.web.model

import io.cutebot.memegen.domain.meme.model.MemeTextArea


class TextAreas: ArrayList<TextArea>() {
    fun getModel(): List<MemeTextArea> {
        return this.map { MemeTextArea(it.position, it.textColor.toColor(), it.bgColor.toColor()) }
    }
}
