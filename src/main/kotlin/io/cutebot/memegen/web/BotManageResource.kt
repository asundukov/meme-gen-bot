package io.cutebot.memegen.web

import io.cutebot.memegen.service.manage.BotManageService
import io.cutebot.memegen.service.LongPollService
import io.cutebot.memegen.web.model.CreateBotRequest
import io.cutebot.memegen.web.model.GetBotResponse
import io.cutebot.memegen.web.model.UpdateBotRequest
import io.cutebot.telegram.TelegramService
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping(
        path = ["/api/manage/bots"],
        produces = [APPLICATION_JSON_VALUE]
)
class BotManageResource(
        private val service: BotManageService,
        private val longPollService: LongPollService,
        private val telegramService: TelegramService
) {

    @PostMapping(consumes = [APPLICATION_JSON_VALUE])
    fun create(
            @Valid @RequestBody bot: CreateBotRequest
    ): GetBotResponse {
        val newBot = bot.getCreateModel()
        val tgUser = telegramService.getMe(newBot.token)
        val createdBot = service.save(newBot, tgUser.userName!!)
        longPollService.startLongPoll(createdBot)
        return GetBotResponse(createdBot)
    }

    @GetMapping("/{bot_id}")
    fun get(
            @PathVariable("bot_id") botId: Int
    ): GetBotResponse {
        return GetBotResponse(service.getById(botId))
    }

    @PostMapping("/{bot_id}")
    fun update(
        @PathVariable("bot_id") botId: Int,
        @Valid @RequestBody bot: UpdateBotRequest
    ): GetBotResponse {
        return GetBotResponse(service.updateBot(botId, bot.getUpdateModel()))
    }

    @GetMapping
    fun getBots(
            @RequestParam("user_id") userId: Long
    ): List<GetBotResponse> {
        return service.getExistedByUserId(userId).map { GetBotResponse(it) }
    }
}
