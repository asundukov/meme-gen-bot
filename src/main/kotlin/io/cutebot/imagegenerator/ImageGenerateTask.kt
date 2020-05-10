package io.cutebot.imagegenerator

import io.cutebot.markonimage.service.messagehandlers.photo.PhotoMessageHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.AlphaComposite
import java.awt.Graphics2D
import java.awt.Image
import java.awt.image.BufferedImage
import java.io.File
import java.math.BigDecimal
import java.net.URL
import java.util.UUID
import javax.imageio.ImageIO
import kotlin.math.min
import kotlin.math.round

class ImageGenerateTask(
        private val originalImageUrl: String,
        private val markImagePath: String,
        private val imageReceiver: ImageReceiver,
        private val scaleValue: BigDecimal,
        private val markPosition: MarkPosition
) : Runnable {

    override fun run() {
        try {
            val original: BufferedImage = ImageIO.read(URL(originalImageUrl))

            val originalW = original.width
            val originalH = original.height

            val markImage = ImageIO.read(File(markImagePath))

            val markW = markImage.width
            val markH = markImage.height

            val wRatio = originalW.toDouble() / markW
            val hRatio = originalH.toDouble() / markH

            val minRatio = min(hRatio, wRatio)

            val scaleSize = scaleValue.toDouble() * minRatio

            val scaledMark = PhotoMessageHandler.toBufferedImage(markImage.getScaledInstance(round(markW * scaleSize).toInt(), round(markH * scaleSize).toInt(), Image.SCALE_SMOOTH)!!)

            val g: Graphics2D = original.createGraphics()
            g.composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER)

            val x = if (markPosition.isLeftAlign()) {
                0
            } else {
                originalW - scaledMark.width
            }

            val y = if (markPosition.isTopAlign()) {
                0
            } else {
                originalH - scaledMark.height
            }

            g.drawImage(scaledMark, x, y, null)
            g.dispose()


            val uuid = UUID.randomUUID().toString()
            val finalPath = "D://$uuid.png"
            ImageIO.write(original, "png", File(finalPath))

            imageReceiver.receive(finalPath)

        } catch (e: Exception) {
            e.printStackTrace()
            imageReceiver.fail(e.message ?: "Error during image generation")
        }
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(ImageGenerateTask::class.java)
    }
}
