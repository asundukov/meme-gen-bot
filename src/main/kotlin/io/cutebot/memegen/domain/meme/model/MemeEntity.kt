package io.cutebot.memegen.domain.meme.model


import io.cutebot.memegen.domain.bot.model.BotEntity
import org.hibernate.annotations.CascadeType
import org.hibernate.annotations.OrderBy
import java.util.Calendar
import javax.persistence.CascadeType.ALL
import javax.persistence.CascadeType.PERSIST
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany


@Entity(name = "meme")
class MemeEntity (
        @Id
        @GeneratedValue(strategy = IDENTITY)
        @Column(updatable = false)
        val memeId: Int,

        @ManyToOne
        @JoinColumn(name = "bot_id", updatable = false)
        val bot: BotEntity,

        @OneToMany(mappedBy = "meme", cascade = [ALL], orphanRemoval = true)
        @OrderBy(clause = "num")
        val areas: MutableList<MemeTextAreaEntity>,

        var totalGenerated: Int,

        @Column(updatable = false)
        val createdOn: Calendar,

        var alias: String,

        @Column(name="is_active")
        var active: Boolean,

        val width: Int,

        val height: Int,

        val thumbWidth: Int,

        val thumbHeight: Int
)
