package io.cutebot.memegen.domain.meme.model


import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne


@Entity(name = "meme_text_area")
class MemeTextAreaEntity (
        @Id
        @GeneratedValue(strategy = IDENTITY)
        @Column(updatable = false)
        val memeTextAreaId: Int,

        @ManyToOne
        @JoinColumn(name = "meme_id")
        val meme: MemeEntity,

        val num: Int,

        val topPos: BigDecimal,
        val bottomPos: BigDecimal,
        val leftPos: BigDecimal,
        val rightPos: BigDecimal,

        val textColorRed: Int,
        val textColorGreen: Int,
        val textColorBlue: Int,
        val textColorAlpha: Int,

        val bgColorRed: Int,
        val bgColorGreen: Int,
        val bgColorBlue: Int,
        val bgColorAlpha: Int
)
