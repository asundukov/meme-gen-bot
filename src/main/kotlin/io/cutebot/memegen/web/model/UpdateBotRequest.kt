package io.cutebot.memegen.web.model

import com.fasterxml.jackson.annotation.JsonProperty
import io.cutebot.memegen.domain.bot.model.UpdateBot

class UpdateBotRequest (
        @field: JsonProperty
        private val token: String?,

        @field: JsonProperty
        private val title: String
) {
    fun getUpdateModel(): UpdateBot {
        return UpdateBot(
                token = token,
                title = title
        )
    }
}
