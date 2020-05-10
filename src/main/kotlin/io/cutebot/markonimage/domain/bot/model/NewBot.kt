package io.cutebot.markonimage.domain.bot.model

data class NewBot(
        override val token: String,
        override val adminUsrId: Long
): Bot
