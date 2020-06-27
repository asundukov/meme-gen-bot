package io.cutebot.markonimage.service.messagehandlers

import io.cutebot.markonimage.service.manage.MarkManageService
import io.cutebot.telegram.TelegramService
import io.cutebot.telegram.handlers.BaseBot
import io.cutebot.telegram.tgmodel.TgSendPhoto
import io.cutebot.telegram.tgmodel.TgUser
import org.springframework.stereotype.Service
import java.io.File

@Service
class MarkMessageHandler(
        val markManageService: MarkManageService,
        val telegramService: TelegramService
): MessageHandler {
    override fun handle(bot: BaseBot, params: String, chatId: Long, user: TgUser): String {
        val markId = params.toIntOrNull() ?: return "I don't understand your choice. Try again please."

        val mark = markManageService.selectMark(markId, bot.id, user)

        telegramService.sendPhoto(bot.token, TgSendPhoto(
                chatId = chatId,
                photo = File(markManageService.getMarkPath(mark)),
                caption = "This mark was chosen for all your future images"
        ))

        return ""
    }

    override fun getCommandDescription(bot: BaseBot): String? = null
}
