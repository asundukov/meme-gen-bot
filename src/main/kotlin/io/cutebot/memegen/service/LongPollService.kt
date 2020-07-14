package io.cutebot.memegen.service

import io.cutebot.memegen.domain.bot.model.ExistedBot
import io.cutebot.memegen.service.bot.BotHandlerFactory
import io.cutebot.memegen.service.manage.BotManageService
import io.cutebot.telegram.BotRunner
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class LongPollService(
        private val botManageService: BotManageService,
        private val botHandlerFactory: BotHandlerFactory
) {

    val botRunner = BotRunner()

    @PostConstruct
    fun init() {
        val bots = botManageService.getAll()
        startLongPolling(bots)
    }

    private fun startLongPolling(bots: List<ExistedBot>) {
        for (existedBot in bots) {
            startLongPoll(existedBot)
        }
    }

    fun startLongPoll(existedBot: ExistedBot) {
        botRunner.run(botHandlerFactory.generateBotHandler(existedBot))
    }
}
