package io.cutebot.memegen.service.manage

import io.cutebot.memegen.domain.bot.model.BotEntity
import io.cutebot.memegen.domain.meme.MemeRepository
import io.cutebot.memegen.domain.meme.model.ExistedMeme
import io.cutebot.memegen.domain.meme.model.MemeEntity
import io.cutebot.memegen.domain.meme.model.MemeTextAreaEntity
import io.cutebot.memegen.domain.meme.model.NewMeme
import io.cutebot.memegen.domain.meme.model.UpdateMeme
import io.cutebot.memegen.service.exception.MarkNotFoundException
import org.springframework.beans.factory.annotation.Value
import org.springframework.orm.ObjectRetrievalFailureException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.awt.Image.SCALE_SMOOTH
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.util.Calendar
import javax.imageio.ImageIO
import kotlin.math.max
import kotlin.math.roundToInt


@Service
class MemeManageService(
        private val repository: MemeRepository,
        private val botManageService: BotManageService,
        @Value("\${meme.dir}")
        private val dirPath: String,

        @Value("\${meme.max-width}")
        private val maxWidth: Int,

        @Value("\${meme.max-height}")
        private val maxHeight: Int,

        @Value("\${meme.thumb-side}")
        private val thumbSide: Int

) {

    fun add(meme: NewMeme): ExistedMeme {
        val botId = meme.botId
        val botEntity = botManageService.getExistedById(botId)
        val textAreas = ArrayList<MemeTextAreaEntity>()

        val newEntity = MemeEntity(
                memeId = 0,
                bot = botEntity,
                totalGenerated = 0,
                createdOn = Calendar.getInstance(),
                alias = meme.alias,
                active = true,
                areas = textAreas
        )

        var i = 1;
        for (textArea in meme.textAreas) {
            textAreas.add(MemeTextAreaEntity(
                    memeTextAreaId = 0,
                    meme = newEntity,
                    leftPos = textArea.left,
                    rightPos = textArea.right,
                    topPos = textArea.top,
                    bottomPos = textArea.bottom,
                    num = i
            ))
            i++
        }

        val existed = saveToBd(newEntity)

        try {
            saveResizedImages(existed, meme.image)
        } catch (e: IOException) {
            delete(newEntity)
            throw e
        }

        return existed
    }

    fun saveResizedImages(meme: ExistedMeme, imageStream: InputStream) {
        val id = meme.id
        val botId = meme.botId
        val fullDirPath = "$dirPath/$botId"
        val originalPath = "$fullDirPath/$id.jpg"
        val thumbPath = "$fullDirPath/${id}_thumb.jpg"

        val img = ImageIO.read(imageStream)
        val originalImage =
        getResizedBufferedImage(img)

        Files.createDirectories(Paths.get(fullDirPath))

        val file = File(originalPath)
        ImageIO.write(originalImage, "jpeg", file)

        val thumbFile = File(thumbPath)
        val thumbImg = getThumbBufferedImage(img)
        ImageIO.write(thumbImg, "jpeg", thumbFile)

    }

    private fun getResizedBufferedImage(img: BufferedImage): BufferedImage {
        val ratio = max(img.width.toDouble() / maxWidth, img.height.toDouble() / maxHeight)
        val w = (img.width / ratio).roundToInt()
        val h = (img.height / ratio).roundToInt()
        return scaleToBuffered(img, w, h)
    }

    private fun getThumbBufferedImage(img: BufferedImage): BufferedImage {
//        val minSide = min(img.width, img.height)
//        val croppedImg = img.getSubimage(img.width - minSide, img.height - minSide, minSide, minSide)
//        return scaleToBuffered(croppedImg, thumbSide, thumbSide)
        val ratio = max(img.width.toDouble() / thumbSide, img.height.toDouble() / thumbSide)
        val w = (img.width / ratio).roundToInt()
        val h = (img.height / ratio).roundToInt()
        return scaleToBuffered(img, w, h)
    }

    private fun scaleToBuffered(croppedImg: BufferedImage, w: Int, h: Int): BufferedImage {
        val tmpImg = croppedImg.getScaledInstance(w, h, SCALE_SMOOTH)
        val newImage = BufferedImage(w, h, TYPE_INT_RGB)
        val g = newImage.graphics
        g.drawImage(tmpImg, 0, 0, null)
        g.dispose()
        return newImage
    }

    @Transactional
    fun saveToBd(meme: MemeEntity): ExistedMeme {
        return ExistedMeme(repository.save(meme))
    }

    @Transactional
    fun delete(meme: MemeEntity) {
        repository.delete(meme)
    }

    @Transactional(readOnly = true)
    fun findByBotAndAliasLike(botId: Int, alias: String): List<ExistedMeme> {
        val bot = botManageService.getExistedById(botId)
        return repository.findAllByBotAndAliasLikeAndActiveIsTrue(bot, "%$alias%").map { ExistedMeme(it) }
    }

    @Transactional(readOnly = true)
    fun getById(id: Int): ExistedMeme {
        return ExistedMeme(getExistedEntityById(id))
    }

    @Transactional
    fun deleteById(memeId: Int) {
        val meme = getExistedEntityById(memeId)
        meme.active = false
        repository.save(meme)
    }


    @Transactional(readOnly = true)
    fun getAllActive(botId: Int): List<ExistedMeme> {
        val botEntity = botManageService.getExistedById(botId)
        return getAllActive(botEntity).map { item -> ExistedMeme(item) }
    }

    @Transactional
    fun update(markId: Int, updateMeme: UpdateMeme): ExistedMeme {
        val meme = getExistedEntityById(markId)
        meme.alias = updateMeme.alias
        meme.areas.clear()
        var i = 1;
        for (textArea in updateMeme.textAreas) {
            meme.areas.add(MemeTextAreaEntity(
                    memeTextAreaId = 0,
                    meme = meme,
                    leftPos = textArea.left,
                    rightPos = textArea.right,
                    topPos = textArea.top,
                    bottomPos = textArea.bottom,
                    num = i
            ))
            i++
        }

        repository.save(meme)
        return ExistedMeme(meme)
    }

    internal fun getAllActive(botEntity: BotEntity): List<MemeEntity> {
        return repository.findAllByActiveIsTrueAndBot(botEntity)
    }

    internal fun getExistedEntityById(id: Int): MemeEntity {
        try {
            return repository.getOne(id)
        } catch (e: ObjectRetrievalFailureException) {
            throw MarkNotFoundException(id)
        }
    }

    fun getMarkPath(meme: ExistedMeme): String {
        val botId = meme.botId
        val id = meme.id
        val fullDirPath = "$dirPath/$botId"
        val path = "$fullDirPath/$id.png"

        return path
    }

    internal fun save(entity: MemeEntity) {
        repository.save(entity)
    }


}
