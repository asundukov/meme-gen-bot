package io.cutebot.memegen.domain.meme.model


import io.cutebot.memegen.domain.bot.model.BotEntity
import java.util.Calendar
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne


@Entity(name = "meme")
class MemeEntity (
        @Id
        @GeneratedValue(strategy = IDENTITY)
        @Column(updatable = false)
        val memeId: Int,

        @ManyToOne
        @JoinColumn(name = "bot_id", updatable = false)
        val bot: BotEntity,

        var totalGenerated: Int,

        @Column(updatable = false)
        val createdOn: Calendar,

        var title: String,

        var description: String,

        @Column(name="is_active")
        var active: Boolean
)
