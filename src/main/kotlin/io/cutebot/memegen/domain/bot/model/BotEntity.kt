package io.cutebot.memegen.domain.bot.model

import java.util.Calendar
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id

@Entity(name = "bot")
data class BotEntity(
        @Id
        @GeneratedValue(strategy = IDENTITY)
        @Column(updatable = false)
        val botId: Int,

        @Column(updatable = false)
        val adminUsrId: Long,

        var token: String,

        var totalGenerated: Int,

        var title: String,

        var username: String,

        @Column(updatable = false)
        val createdOn: Calendar
)
