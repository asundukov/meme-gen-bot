package io.cutebot.markonimage.domain.bot.model

import io.cutebot.markonimage.domain.mark.model.MarkEntity
import java.util.Calendar
import javax.persistence.Column
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
        @Column(updatable = false)
        val botId: Int,

        @Column(updatable = false)
        val adminUsrId: Long,

        var token: String,

        var totalImages: Int,

        var title: String,

        @ManyToOne
        @JoinColumn(name = "default_mark_id")
        var defaultMark: MarkEntity?,

        @Column(updatable = false)
        val createdOn: Calendar
)
