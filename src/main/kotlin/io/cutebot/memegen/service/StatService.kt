package io.cutebot.memegen.service

import io.cutebot.memegen.service.manage.BotManageService
import io.cutebot.memegen.service.manage.MemeManageService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StatService(
        private val botManageService: BotManageService,
        private val memeManageService: MemeManageService
) {

    @Transactional
    fun addMarkImageGenerate(markId: Int) {
        val meme = memeManageService.getExistedEntityById(markId)
        meme.totalGenerated++
        meme.bot.totalGenerated++

        botManageService.save(meme.bot)
        memeManageService.save(meme)
    }

}
