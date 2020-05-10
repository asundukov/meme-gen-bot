package io.cutebot.markonimage.web

import io.cutebot.markonimage.service.manage.BotManageService
import io.cutebot.markonimage.service.LongPollService
import io.cutebot.markonimage.web.model.CreateBotRequest
import io.cutebot.markonimage.web.model.GetBotResponse
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping(
        path = ["/api/manage/bot"],
        produces = [APPLICATION_JSON_VALUE]
)
class BotManageResource(
        private val service: BotManageService,
        private val longPollService: LongPollService
) {

    @PostMapping(consumes = [APPLICATION_JSON_VALUE])
    fun create(
            @Valid @RequestBody bot: CreateBotRequest
    ): GetBotResponse {
        val createdBot = service.save(bot.getCreateModel())
        longPollService.startLongPoll(createdBot)
        return GetBotResponse(createdBot)
    }

    @GetMapping("/{bot_id}")
    fun get(
            @PathVariable("bot_id") botId: Int
    ): GetBotResponse {
        return GetBotResponse(service.getById(botId))
    }
}
