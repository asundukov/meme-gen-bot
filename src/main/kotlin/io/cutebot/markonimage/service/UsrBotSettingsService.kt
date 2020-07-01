package io.cutebot.markonimage.service

import io.cutebot.markonimage.domain.bot.model.BotEntity
import io.cutebot.markonimage.domain.mark.model.MarkEntity
import io.cutebot.markonimage.domain.usr.model.UsrEntity
import io.cutebot.markonimage.domain.usrbotsettings.UsrBotSettingsRepository
import io.cutebot.markonimage.domain.usrbotsettings.model.UsrBotSettingsEntity
import org.springframework.stereotype.Service
import java.util.Calendar

@Service
class UsrBotSettingsService(
        private val repository: UsrBotSettingsRepository
) {

    internal fun getSelectedMark(botEntity: BotEntity, usrEntity: UsrEntity): MarkEntity {
        val entity = getOrCreate(botEntity, usrEntity)
        return entity.selectedMark
    }

    internal fun getOrCreate(bot: BotEntity, usr: UsrEntity, defaultMark: MarkEntity? = null): UsrBotSettingsEntity {
        val existed = repository.findByUsrAndBot(usr, bot)
        if (existed != null) {
            return existed
        }

        val newEntity = UsrBotSettingsEntity(
                usrBotSettingsId = 0,
                usr = usr,
                bot = bot,
                createdOn = Calendar.getInstance(),
                isBlocked = false,
                selectedMark = defaultMark ?: bot.defaultMark!!
        )
        repository.save(newEntity)
        return newEntity
    }

    internal fun updateSelected(botEntity: BotEntity, usrEntity: UsrEntity, markEntity: MarkEntity) {
        val entity = getOrCreate(botEntity, usrEntity, markEntity)
        entity.selectedMark = markEntity
        repository.save(entity)
    }

}
