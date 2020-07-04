package io.cutebot.imagegenerator

import io.cutebot.memegen.service.messagehandlers.photo.PhotoMessageHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.AlphaComposite
import java.awt.Graphics2D
import java.awt.Image
import java.awt.image.BufferedImage
import java.io.File
import java.net.URL
import java.util.UUID
import javax.imageio.ImageIO
import kotlin.math.min
import kotlin.math.round

class ImageGenerateTask(
        private val originalImageUrl: String,
        private val markImagePath: String,
        private val imageReceiver: ImageReceiver,
        private val imageDir: String
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

            val scaleSize = 1 * minRatio

            val scaledMark = PhotoMessageHandler.toBufferedImage(markImage.getScaledInstance(round(markW * scaleSize).toInt(), round(markH * scaleSize).toInt(), Image.SCALE_SMOOTH)!!)

            val g: Graphics2D = original.createGraphics()
            g.composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER)

            val x = 0

            val y = 0

            g.drawImage(scaledMark, x, y, null)
            g.dispose()


            val uuid = UUID.randomUUID().toString()
            val finalPath = "$imageDir/$uuid.png"
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
