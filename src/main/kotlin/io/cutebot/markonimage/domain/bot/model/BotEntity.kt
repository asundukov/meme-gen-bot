package io.cutebot.markonimage.domain.bot.model

import io.cutebot.markonimage.domain.mark.model.MarkEntity
import java.util.Calendar
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity(name = "bot")
data class BotEntity(
        @Id
        @GeneratedValue(strategy = IDENTITY)
        val botId: Int,

        val adminUsrId: Long,

        val token: String,

        val totalImages: Int,

        @ManyToOne
        @JoinColumn(name = "default_mark_id")
        var defaultMark: MarkEntity?,

        val createdOn: Calendar
)
