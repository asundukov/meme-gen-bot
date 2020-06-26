package io.cutebot.markonimage.domain.mark.model


import io.cutebot.markonimage.domain.bot.model.BotEntity
import java.math.BigDecimal
import java.util.Calendar
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne


@Entity(name = "mark")
class MarkEntity (
        @Id
        @GeneratedValue(strategy = IDENTITY)
        @Column(updatable = false)
        val markId: Int,

        @ManyToOne
        @JoinColumn(name = "bot_id", updatable = false)
        val bot: BotEntity,

        var position: Int,

        var sizeValue: BigDecimal,

        var totalImages: Int,

        @Column(updatable = false)
        val createdOn: Calendar,

        var title: String,

        var description: String

)
