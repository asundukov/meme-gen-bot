package io.cutebot.memegen.service

import io.cutebot.memegen.domain.meme.model.ExistedArea
import io.cutebot.memegen.service.manage.MemeManageService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.awt.AlphaComposite
import java.awt.Color
import java.awt.Color.BLACK
import java.awt.Font
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_ARGB
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.UUID
import javax.imageio.ImageIO
import javax.swing.JTextArea
import javax.swing.JTextPane
import javax.swing.text.SimpleAttributeSet
import javax.swing.text.StyleConstants
import kotlin.math.min


@Service
class ImageGenerator(
        @Value("\${meme.dir}")
        private val memeDir: String,
        private val memeManageService: MemeManageService
) {

    fun generate(memeId: Int, query: String): String {
        val meme = memeManageService.getById(memeId)
        val botId = meme.botId
        val filePath = "$memeDir/$botId/$memeId.jpg"

        if (query.isEmpty()) {
            return filePath
        }

        try {
            val original: BufferedImage = ImageIO.read(File(filePath))
            val g: Graphics2D = original.createGraphics()

            val areaPreparations = ArrayList<AreaPreparation>()
            var queries = query.split('|')
            queries = queries.map { it.trim() }

            val maxItem = min(meme.areas.size, queries.size)

            for (x in 0 until  maxItem) {
                areaPreparations.add(AreaPreparation(
                        area = meme.areas[x],
                        text = queries[x],
                        fontName = g.font.fontName,
                        imageHeight = original.height,
                        imageWidth = original.width
                ))
            }

            val minSize = areaPreparations.stream()
                    .map { it.getFontSize() }
                    .min(Int::compareTo)
                    .get()

            areaPreparations.forEach { it.ensureMaxFonSize((minSize * 1.15).toInt()) }

            log.info("Original image size {}, {}", original.width, original.height)

            areaPreparations.forEach { it.getImage() }

            g.composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER)
            g.color = BLACK

            areaPreparations.forEach {
                log.info("Draw text at {}, {}", it.left.toInt(), it.top.toInt())
                g.drawImage(it.getImage(), it.left.toInt(), it.getActualTop(), null)
            }
            g.dispose()

            val uuid = UUID.randomUUID().toString()
            val dir = "$memeDir/generated"
            Files.createDirectories(Paths.get(dir))
            val finalPath = "$dir/$uuid.jpg"

            ImageIO.write(original, "jpg", File(finalPath))

            return finalPath
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    fun generateThumb(memeId: Int, query: String): String {
        val meme = memeManageService.getById(memeId)
        val botId = meme.botId

        return "$memeDir/$botId/${memeId}_thumb.jpg"
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(ImageGenerator::class.java)
    }

    class AreaPreparation(
            area: ExistedArea,
            imageWidth: Int, imageHeight: Int,
            private val text: String,
            private val fontName: String
    ) {
        val textColor = area.textColor
        val bgColor = area.bgColor
        val left =  area.left.toFloat() * imageWidth / 100
        val top = area.top.toFloat() * imageHeight / 100

        private val right =  area.right.toFloat() * imageWidth / 100
        private val bottom = area.bottom.toFloat() * imageHeight / 100

        private val width = (right - left).toInt()
        private val height = (bottom - top).toInt()

        private var calculatedSize: Int? = null

        private var bufferedImage: BufferedImage? = null

        fun getImage(): BufferedImage {
            if (bufferedImage != null) {
                return bufferedImage!!
            }
            val textPane = JTextPane()
            textPane.setSize(width, height)
            textPane.background = bgColor

            val doc = textPane.styledDocument
            val pStyle = SimpleAttributeSet()
            StyleConstants.setAlignment(pStyle, StyleConstants.ALIGN_CENTER)
            StyleConstants.setFontSize(pStyle, getFontSize())
            StyleConstants.setFontFamily(pStyle, fontName)
            StyleConstants.setBold(pStyle, true)
            StyleConstants.setForeground(pStyle, textColor)

            doc.insertString(0, text, pStyle)
            doc.setParagraphAttributes(0, doc.length, pStyle, true)

            val imageHeight = textPane.preferredSize.height
            val img = BufferedImage(width, imageHeight, TYPE_INT_ARGB)
            textPane.paint(img.graphics)
            img.graphics.dispose()

            log.info("Draw text {} size {},{}", text, width, imageHeight)
            bufferedImage = img

            return img
        }

        fun getActualTop(): Int {
            return (top + (height - getImage().height) / 2).toInt()
        }

        fun getFontSize(): Int {
            return calculatedSize ?: calculateFontSize()
        }

        fun ensureMaxFonSize(maxSize: Int) {
            if (getFontSize() > maxSize) {
                calculatedSize = maxSize
            }
        }

        private fun calculateFontSize(): Int {
            val textArea = JTextArea(text)
            textArea.setSize(width, height)

            var minFontSize = height
            textArea.lineWrap = true
            textArea.wrapStyleWord = true
            textArea.font = Font(fontName, Font.BOLD, minFontSize)

            while (textArea.preferredSize.height > height && minFontSize > 1) {
                minFontSize--
                textArea.font = Font(fontName, Font.BOLD, minFontSize)
            }

            textArea.lineWrap = false
            val words = text.split(' ')
            words.forEach {
                textArea.text = it
                while (textArea.preferredSize.width > width && minFontSize > 1) {
                    minFontSize--
                    textArea.font = Font(fontName, Font.BOLD, minFontSize)
                }
            }


            calculatedSize = minFontSize
            return minFontSize
        }
    }
}
