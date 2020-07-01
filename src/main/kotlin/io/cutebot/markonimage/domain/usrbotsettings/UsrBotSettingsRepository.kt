package io.cutebot.markonimage.domain.usrbotsettings

import io.cutebot.markonimage.domain.bot.model.BotEntity
import io.cutebot.markonimage.domain.usr.model.UsrEntity
import io.cutebot.markonimage.domain.usrbotsettings.model.UsrBotSettingsEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UsrBotSettingsRepository: JpaRepository<UsrBotSettingsEntity, Int> {

    fun findByUsrAndBot(usr: UsrEntity, bot: BotEntity): UsrBotSettingsEntity?

}
