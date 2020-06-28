package io.cutebot.markonimage.service

import io.cutebot.markonimage.service.manage.BotManageService
import io.cutebot.markonimage.service.manage.MarkManageService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StatService(
        private val botManageService: BotManageService,
        private val markManageService: MarkManageService
) {

    @Transactional
    fun addMarkImageGenerate(markId: Int) {
        val mark = markManageService.getExistedEntityById(markId)
        mark.totalImages++
        mark.bot.totalImages++

        botManageService.save(mark.bot)
        markManageService.save(mark)
    }

}
