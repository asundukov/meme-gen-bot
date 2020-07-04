package io.cutebot.memegen.domain.bot

import io.cutebot.memegen.domain.bot.model.BotEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BotRepository: JpaRepository<BotEntity, Int> {
    fun findByToken(token: String): BotEntity?
    fun findAllByAdminUsrId(userId: Long): List<BotEntity>
}
