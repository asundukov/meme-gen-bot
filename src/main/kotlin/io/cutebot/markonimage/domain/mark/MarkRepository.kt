package io.cutebot.markonimage.domain.mark

import io.cutebot.markonimage.domain.bot.model.BotEntity
import io.cutebot.markonimage.domain.mark.model.MarkEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MarkRepository: JpaRepository<MarkEntity, Int> {
    fun findAllByActiveIsTrueAndBot(botEntity: BotEntity): List<MarkEntity>
}
