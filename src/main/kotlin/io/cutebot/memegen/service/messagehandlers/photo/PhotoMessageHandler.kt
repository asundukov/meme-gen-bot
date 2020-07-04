package io.cutebot.memegen.service.messagehandlers.photo

import io.cutebot.imagegenerator.ImageGenerateExecutor
import io.cutebot.memegen.service.StatService
import io.cutebot.memegen.service.manage.MemeManageService
import io.cutebot.telegram.TelegramService
import io.cutebot.telegram.handlers.BaseBot
import io.cutebot.telegram.tgmodel.TgDocument
import io.cutebot.telegram.tgmodel.TgUser
import io.cutebot.telegram.tgmodel.photo.TgPhotoSize
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.awt.Image
import java.awt.image.BufferedImage


@Service
class PhotoMessageHandler(
        private val telegramService: TelegramService,
        private val memeManageService: MemeManageService,
        private val statService: StatService,
        private val imageGenerateExecutor: ImageGenerateExecutor
) {

    fun handlePhoto(bot: BaseBot, photo: List<TgPhotoSize>, chatId: Long, usr: TgUser) {
        handle(bot, photo[photo.size - 1].fileId, chatId, usr, AttachmentType.PHOTO)
    }

    fun handleDocument(bot: BaseBot, photo: TgDocument, chatId: Long, usr: TgUser) {
        handle(bot, photo.fileId, chatId, usr, AttachmentType.FILE)
    }

    private fun handle(bot: BaseBot, fileId: String, chatId: Long, usr: TgUser, attachmentType: AttachmentType) {
        val mark = memeManageService.getById(1)

        val f = telegramService.getFile(bot.token, fileId)

        val originalFileUrl = telegramService.getDownloadUrl(bot.token, f.filePath)
        val markFilePath = memeManageService.getMarkPath(mark)

        imageGenerateExecutor.execute(
                originalImageUrl = originalFileUrl,
                markImagePath = markFilePath,
                imageReceiver = PhotoSender(
                        telegramService = telegramService,
                        bot = bot,
                        chatId = chatId,
                        attachmentType = attachmentType,
                        incrementFunc = { statService.addMarkImageGenerate(mark.id) }
                )
        )

    }

    companion object {
        private val logger = LoggerFactory.getLogger(PhotoMessageHandler::class.java)

        fun toBufferedImage(img: Image): BufferedImage {
            if (img is BufferedImage) {
                return img
            }

            // Create a buffered image with transparency
            val bimage = BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB)

            // Draw the image on to the buffered image
            val bGr = bimage.createGraphics()
            bGr.drawImage(img, 0, 0, null)
            bGr.dispose()

            // Return the buffered image
            return bimage
        }
    }
}