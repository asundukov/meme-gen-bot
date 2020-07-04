package io.cutebot.memegen.service.manage

import io.cutebot.memegen.domain.bot.model.BotEntity
import io.cutebot.memegen.domain.meme.MemeRepository
import io.cutebot.memegen.domain.meme.model.ExistedMeme
import io.cutebot.memegen.domain.meme.model.MemeEntity
import io.cutebot.memegen.domain.meme.model.NewMeme
import io.cutebot.memegen.domain.meme.model.UpdateMeme
import io.cutebot.memegen.service.exception.MarkNotFoundException
import org.springframework.beans.factory.annotation.Value
import org.springframework.orm.ObjectRetrievalFailureException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.Calendar

@Service
class MemeManageService(
        private val repository: MemeRepository,
        private val botManageService: BotManageService,
        @Value("\${meme.dir}")
        private val dirPath: String
) {
    @Transactional
    fun add(meme: NewMeme): ExistedMeme {
        val botId = meme.botId
        val botEntity = botManageService.getExistedById(botId)
        val newEntity = MemeEntity(
                memeId = 0,
                bot = botEntity,
                totalGenerated = 0,
                createdOn = Calendar.getInstance(),
                title = meme.title,
                description = meme.description,
                active = true
        )
        save(newEntity)

        val id = newEntity.memeId
        val fullDirPath = "$dirPath/$botId"
        val path = "$fullDirPath/$id.png"
        try {
            val buffer = meme.image.readAllBytes()
            val file = File(path)
            Files.createDirectories(Paths.get(fullDirPath))
            val outStream = FileOutputStream(file)
            outStream.write(buffer)
        } catch (e: IOException) {
            repository.delete(newEntity)
            throw e
        }

        return ExistedMeme(newEntity)
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
    fun update(markId: Int, updateMeme: UpdateMeme) {
        val mark = getExistedEntityById(markId)
        mark.title = updateMeme.title
        mark.description = updateMeme.description
        repository.save(mark)
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
