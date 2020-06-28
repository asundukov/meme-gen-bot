package io.cutebot.markonimage.service.messagehandlers.photo

import io.cutebot.imagegenerator.ImageReceiver
import io.cutebot.telegram.TelegramService
import io.cutebot.telegram.handlers.BaseBot
import io.cutebot.telegram.tgmodel.TgSendDocument
import io.cutebot.telegram.tgmodel.TgSendPhoto
import io.cutebot.telegram.tgmodel.TgSendTextMessage
import java.io.File

class PhotoSender(
        private val telegramService: TelegramService,
        private val bot: BaseBot,
        private val chatId: Long,
        private val attachmentType: AttachmentType,
        private val incrementFunc: () -> Unit
) : ImageReceiver {

    override fun receive(filePath: String) {
        val f = File(filePath)

        incrementFunc.invoke()

        if (attachmentType == AttachmentType.PHOTO) {
            val tgSendPhoto = TgSendPhoto(chatId, f)
            telegramService.sendPhoto(bot.token, tgSendPhoto)
        } else {
            val tgSendDocument = TgSendDocument(chatId, f)
            telegramService.sendDocument(bot.token, tgSendDocument)
        }

    }

    override fun fail(reason: String) {
        val message = TgSendTextMessage(chatId, "Error $reason")
        telegramService.sendMessage(bot.token, message)
    }

}