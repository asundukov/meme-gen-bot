package io.cutebot.memegen.service

import io.cutebot.memegen.domain.bot.model.ExistedBot
import io.cutebot.memegen.service.manage.BotManageService
import io.cutebot.telegram.handlers.BaseBot
import io.cutebot.telegram.handlers.LongPollProcess
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class LongPollService(
        private val botManageService: BotManageService,
        private val botHandleService: BotHandleService,

        @Value("\${telegram.longpoll.enable}")
        private val longPollEnable: Boolean,

        @Value("\${telegram.longpoll.timeout}")
        private val longPollTimeout: Int

) {
    @PostConstruct
    fun init() {
        val bots = botManageService.getAll()

        startLongPolling(bots)
    }

    private fun startLongPolling(bots: List<ExistedBot>) {
        for (bot in bots) {
            botHandleService.setCommands(BaseBot(bot.id, bot.token))
            startLongPoll(bot)
        }
    }

    fun startLongPoll(bot: ExistedBot) {
        if (longPollEnable) {
            LongPollProcess(longPollTimeout, botHandleService, BaseBot(bot.id, bot.token)).start()
        }
    }

}