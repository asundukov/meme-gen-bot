package io.cutebot.memegen.web.model

import com.fasterxml.jackson.annotation.JsonProperty
import io.cutebot.memegen.domain.bot.model.NewBot
import javax.validation.constraints.Min

class CreateBotRequest (
        @field: JsonProperty
        private val token: String,

        @field: JsonProperty
        @field: Min(1)
        private val adminUsrId: Long,

        @field: JsonProperty
        private val title: String
) {
    fun getCreateModel(): NewBot {
        return NewBot(
                token = token,
                adminUsrId = adminUsrId,
                title = title
        )
    }
}
