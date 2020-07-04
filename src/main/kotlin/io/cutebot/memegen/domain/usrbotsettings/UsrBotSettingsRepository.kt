package io.cutebot.memegen.domain.usrbotsettings

import io.cutebot.memegen.domain.bot.model.BotEntity
import io.cutebot.memegen.domain.usr.model.UsrEntity
import io.cutebot.memegen.domain.usrbotsettings.model.UsrBotSettingsEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UsrBotSettingsRepository: JpaRepository<UsrBotSettingsEntity, Int> {

    fun findByUsrAndBot(usr: UsrEntity, bot: BotEntity): UsrBotSettingsEntity?

}
