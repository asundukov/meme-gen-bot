package io.cutebot.markonimage.web.model

import com.fasterxml.jackson.annotation.JsonProperty
import io.cutebot.markonimage.domain.bot.model.NewBot
import javax.validation.constraints.Min

class CreateBotRequest (
        @field:JsonProperty
        private val token: String,

        @field:JsonProperty
        @field: Min(1)
        private val adminUsrId: Long
) {
    fun getCreateModel(): NewBot {
        return NewBot(
                token = token,
                adminUsrId = adminUsrId
        )
    }
}
