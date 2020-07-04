package io.cutebot.memegen.service

import io.cutebot.memegen.domain.bot.model.BotEntity
import io.cutebot.memegen.domain.meme.model.MemeEntity
import io.cutebot.memegen.domain.usr.model.UsrEntity
import io.cutebot.memegen.domain.usrbotsettings.UsrBotSettingsRepository
import io.cutebot.memegen.domain.usrbotsettings.model.UsrBotSettingsEntity
import org.springframework.stereotype.Service
import java.util.Calendar

@Service
class UsrBotSettingsService(
        private val repository: UsrBotSettingsRepository
) {

    internal fun getOrCreate(bot: BotEntity, usr: UsrEntity, defaultMeme: MemeEntity? = null): UsrBotSettingsEntity {
        val existed = repository.findByUsrAndBot(usr, bot)
        if (existed != null) {
            return existed
        }

        val newEntity = UsrBotSettingsEntity(
                usrBotSettingsId = 0,
                usr = usr,
                bot = bot,
                createdOn = Calendar.getInstance(),
                isBlocked = false
        )
        repository.save(newEntity)
        return newEntity
    }
}
