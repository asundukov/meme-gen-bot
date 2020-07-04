package io.cutebot.memegen.domain.usrbotsettings.model

import io.cutebot.memegen.domain.bot.model.BotEntity
import io.cutebot.memegen.domain.usr.model.UsrEntity
import java.util.Calendar
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity(name = "usr_bot_settings")
class UsrBotSettingsEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val usrBotSettingsId: Int,

        @ManyToOne
        @JoinColumn(name = "bot_id")
        val bot: BotEntity,

        @ManyToOne
        @JoinColumn(name = "usr_id")
        val usr: UsrEntity,

        val createdOn: Calendar,

        val isBlocked: Boolean

)
