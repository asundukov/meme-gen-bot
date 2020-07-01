package io.cutebot.markonimage.service.messagehandlers

import io.cutebot.markonimage.service.manage.MarkManageService
import io.cutebot.telegram.TelegramService
import io.cutebot.telegram.handlers.BaseBot
import io.cutebot.telegram.tgmodel.TgSendPhoto
import io.cutebot.telegram.tgmodel.TgUser
import org.springframework.stereotype.Service
import java.io.File

@Service
class MarksMessageHandler(
        private val markManageService: MarkManageService,
        private val telegramService: TelegramService
): MessageHandler {

    override fun handle(bot: BaseBot, params: String, chatId: Long, user: TgUser): String {
        val marks = markManageService.getAllActive(bot.id)

        for (mark in marks) {
            val photo = TgSendPhoto(
                    chatId = chatId,
                    photo = File(markManageService.getMarkPath(mark)),
                    caption = "You can choose this mark to use it on your photos: /mark_" + mark.id
            )
            telegramService.sendPhoto(bot.token, photo)
        }

        return ""
    }

    override fun getCommandDescription(bot: BaseBot): String
            = "Show all possible marks"
}
