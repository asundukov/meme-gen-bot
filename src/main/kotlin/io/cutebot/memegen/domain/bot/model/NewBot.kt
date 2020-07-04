package io.cutebot.memegen.domain.bot.model

data class NewBot(
        val token: String,
        val adminUsrId: Long,
        val title: String
)
