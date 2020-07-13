package io.cutebot.memegen.domain.meme

import io.cutebot.memegen.domain.bot.model.BotEntity
import io.cutebot.memegen.domain.meme.model.MemeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MemeRepository: JpaRepository<MemeEntity, Int> {
    fun findAllByActiveIsTrueAndBot(botEntity: BotEntity): List<MemeEntity>
    fun findAllByBotAndAliasLike(bot: BotEntity, alias: String): List<MemeEntity>
}
