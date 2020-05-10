package io.cutebot.markonimage.domain.mark.model


import io.cutebot.markonimage.domain.bot.model.BotEntity
import java.math.BigDecimal
import java.util.Calendar
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
    val markId: Int,

    @ManyToOne
    @JoinColumn(name = "bot_id")
    val bot: BotEntity,

    val position: Int,

    val sizePercent: BigDecimal,

    val totalImages: Int,

    val createdOn: Calendar

)
