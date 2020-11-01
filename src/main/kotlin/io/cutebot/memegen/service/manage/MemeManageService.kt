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
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.Calendar
import java.util.UUID
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

        val tmpFileId = UUID.randomUUID().toString();
        val sizesData = saveResizedImages(meme, tmpFileId)

        val newEntity = MemeEntity(
                memeId = 0,
                bot = botEntity,
                totalGenerated = 0,
                createdOn = Calendar.getInstance(),
                alias = meme.alias,
                active = true,
                areas = ArrayList(),
                width = sizesData.original.width,
                height = sizesData.original.height,
                thumbWidth = sizesData.thumb.width,
                thumbHeight = sizesData.thumb.height
        )

        newEntity.areas.addAll(getTextAreasEntities(meme, newEntity))

        val existed = saveToBd(newEntity)

        try {
            moveImages(tmpFileId, existed)
        } catch (e: IOException) {
            delete(newEntity);
            throw e;
        }

        return existed
    }

    fun saveResizedImages(meme: NewMeme, tmpFileId: String): SizesData {
        val originalPath = getOriginalFullPath(meme.botId, tmpFileId)
        val thumbPath = getThumbFullPath(meme.botId, tmpFileId)

        val img = ImageIO.read(meme.image)
        val originalImage = getResizedBufferedImage(img)

        val file = File(originalPath)
        ImageIO.write(originalImage, "jpeg", file)

        val thumbFile = File(thumbPath)
        val thumbImg = getThumbBufferedImage(img)
        ImageIO.write(thumbImg, "jpeg", thumbFile)
        return SizesData(
                original = SizeData(originalImage.width, originalImage.height),
                thumb = SizeData(thumbImg.width, thumbImg.height)
        )
    }

    fun moveImages(tmpFileId: String, meme: ExistedMeme) {
        val tmpOriginalPath = getOriginalFullPath(meme.botId, tmpFileId)
        val tmpThumbPath = getThumbFullPath(meme.botId, tmpFileId)

        val originalPath = getOriginalFullPath(meme.botId, meme.id.toString())
        val thumbPath = getThumbFullPath(meme.botId, meme.id.toString())

        Files.move(Path.of(tmpOriginalPath), Path.of(originalPath))
        Files.move(Path.of(tmpThumbPath), Path.of(thumbPath))
    }

    private fun getOriginalFullPath(botId: Int, fileName: String): String {
        val botDir = getBotDirPath(botId)
        return "$botDir/$fileName.jpg"
    }

    private fun getThumbFullPath(botId: Int, fileName: String): String {
        val botDir = getBotDirPath(botId)
        return "$botDir/${fileName}_thumb.jpg"
    }

    private fun getBotDirPath(botId: Int): String {
        val botDir = "$dirPath/$botId"
        Files.createDirectories(Paths.get(botDir))
        return botDir
    }

    private fun getResizedBufferedImage(img: BufferedImage): BufferedImage {
        val ratio = max(img.width.toDouble() / maxWidth, img.height.toDouble() / maxHeight)
        val w = (img.width / ratio).roundToInt()
        val h = (img.height / ratio).roundToInt()
        return scaleToBuffered(img, w, h)
    }

    private fun getThumbBufferedImage(img: BufferedImage): BufferedImage {
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
        meme.areas.addAll(getTextAreasEntities(updateMeme, meme))

        repository.save(meme)
        return ExistedMeme(meme)
    }

    private fun getTextAreasEntities(updateMeme: UpdateMeme, meme: MemeEntity): ArrayList<MemeTextAreaEntity> {
        var i = 1;
        val newAreas = ArrayList<MemeTextAreaEntity>()
        for (textArea in updateMeme.textAreas) {
            newAreas.add(MemeTextAreaEntity(
                    memeTextAreaId = 0,
                    meme = meme,
                    leftPos = textArea.position.left,
                    rightPos = textArea.position.right,
                    topPos = textArea.position.top,
                    bottomPos = textArea.position.bottom,
                    textColorRed = textArea.textColor.red,
                    textColorGreen = textArea.textColor.green,
                    textColorBlue = textArea.textColor.blue,
                    textColorAlpha = textArea.textColor.alpha,
                    bgColorRed = textArea.bgColor.red,
                    bgColorGreen = textArea.bgColor.green,
                    bgColorBlue = textArea.bgColor.blue,
                    bgColorAlpha = textArea.bgColor.alpha,
                    num = i
            ))
            i++
        }
        return newAreas
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


    data class SizesData (
            val original: SizeData,
            val thumb: SizeData
    )

    data class SizeData (
            val width: Int,
            val height: Int
    )

}
