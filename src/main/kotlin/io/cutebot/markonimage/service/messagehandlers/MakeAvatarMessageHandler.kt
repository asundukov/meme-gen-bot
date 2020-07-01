package io.cutebot.markonimage.service.messagehandlers

import io.cutebot.imagegenerator.ImageGenerateExecutor
import io.cutebot.markonimage.service.StatService
import io.cutebot.markonimage.service.manage.MarkManageService
import io.cutebot.markonimage.service.messagehandlers.photo.AttachmentType
import io.cutebot.markonimage.service.messagehandlers.photo.PhotoSender
import io.cutebot.telegram.TelegramService
import io.cutebot.telegram.handlers.BaseBot
import io.cutebot.telegram.tgmodel.TgUser
import org.springframework.stereotype.Service

@Service
class MakeAvatarMessageHandler(
        private val telegramService: TelegramService,
        private val markManageService: MarkManageService,
        private val statService: StatService,
        private val imageGenerateExecutor: ImageGenerateExecutor
): MessageHandler {
    override fun handle(bot: BaseBot, params: String, chatId: Long, user: TgUser): String {

        var num = try {
            params.toInt()
        } catch (e: NumberFormatException) {
            1
        }

        if (num < 1) {
            num = 1
        }

        val userPhotos = telegramService.getUserProfilePhotos(bot.token, user.id)

        if (userPhotos.photos.isEmpty()) {
            return "You don't have any profile photos. Send me an image instead"
        }

        telegramService.sendChatAction(bot.token, chatId, "upload_photo")

        if (num > userPhotos.photos.size) {
            num = userPhotos.photos.size
        }

        val selectedPhoto = userPhotos.photos[num-1].last()

        val mark = markManageService.getSelectedMark(bot.id, user)

        val f = telegramService.getFile(bot.token, selectedPhoto.fileId)

        val originalFileUrl = telegramService.getDownloadUrl(bot.token, f.filePath)
        val markFilePath = markManageService.getMarkPath(mark)

        imageGenerateExecutor.execute(
                originalImageUrl = originalFileUrl,
                markImagePath = markFilePath,
                markPosition = mark.position,
                sizeValue = mark.sizeValue,
                imageReceiver = PhotoSender(
                        telegramService = telegramService,
                        bot = bot,
                        chatId = chatId,
                        attachmentType = AttachmentType.PHOTO,
                        incrementFunc = { statService.addMarkImageGenerate(mark.id) }
                ),
                opacity = mark.opacity
        )

        return ""
    }

    override fun getCommandDescription(bot: BaseBot): String? {
        return "Generate marked avatar from your profile. Add number to generate another avatar."
    }
}
