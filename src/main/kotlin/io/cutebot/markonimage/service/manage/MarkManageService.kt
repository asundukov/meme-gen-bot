package io.cutebot.markonimage.service.manage

import io.cutebot.markonimage.domain.mark.MarkRepository
import io.cutebot.markonimage.domain.mark.model.ExistedMark
import io.cutebot.markonimage.domain.mark.model.MarkEntity
import io.cutebot.imagegenerator.MarkPosition
import io.cutebot.markonimage.domain.mark.model.NewMark
import io.cutebot.markonimage.service.UsrBotSettingsService
import io.cutebot.markonimage.service.exception.MarkNotFoundException
import io.cutebot.telegram.tgmodel.TgUser
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.repository.findByIdOrNull
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
class MarkManageService(
        private val repository: MarkRepository,
        private val botManageService: BotManageService,
        private val usrManageService: UsrManageService,
        private val usrBotSettingsService: UsrBotSettingsService,
        @Value("\${image.dir}")
        private val dirPath: String
) {
    @Transactional
    fun save(mark: NewMark): ExistedMark {
        val botId = mark.botId
        val botEntity = botManageService.getExistedById(botId)
        val newEntity = MarkEntity(
                markId = 0,
                bot = botEntity,
                totalImages = 0,
                createdOn = Calendar.getInstance(),
                position = MarkPosition.idByType(mark.position),
                sizePercent = mark.sizePercent
        )
        repository.save(newEntity)

        val id = newEntity.markId
        val fullDirPath = "$dirPath/$botId"
        val path = "$fullDirPath/$id.png"
        try {
            val buffer = mark.image.readAllBytes()
            val file = File(path)
            Files.createDirectories(Paths.get(fullDirPath))
            val outStream = FileOutputStream(file)
            outStream.write(buffer)
        } catch (e: IOException) {
            repository.delete(newEntity)
            throw e
        }

        if (botEntity.defaultMark == null) {
            botManageService.setDefaultMark(botEntity, newEntity)
        }

        return ExistedMark(newEntity)
    }

    @Transactional(readOnly = true)
    fun getById(id: Int): ExistedMark {
        return ExistedMark(getExistedEntityById(id))
    }

    @Transactional(readOnly = true)
    fun getAll(botId: Int): List<ExistedMark> {
        val botEntity = botManageService.getExistedById(botId)
        return repository.findAllByBot(botEntity).map { item -> ExistedMark(item) }
    }

    @Transactional
    fun selectMark(markId: Int, botId: Int, user: TgUser): ExistedMark {
        val markEntity = repository.findByIdOrNull(markId) ?: throw MarkNotFoundException(markId)
        val botEntity = botManageService.getExistedById(botId)

        if (markEntity.bot.botId != botEntity.botId) {
            throw MarkNotFoundException(markId)
        }

        val usrEntity = usrManageService.getOrCreateById(user)

        usrBotSettingsService.updateSelected(botEntity, usrEntity, markEntity)
        return ExistedMark(markEntity)
    }

    @Transactional
    fun getSelectedMark(botId: Int, user: TgUser): ExistedMark {
        val botEntity = botManageService.getExistedById(botId)
        val usrEntity = usrManageService.getOrCreateById(user)

        return ExistedMark(usrBotSettingsService.getSelectedMark(botEntity, usrEntity))
    }

    internal fun getExistedEntityById(id: Int): MarkEntity {
        try {
            return repository.getOne(id)
        } catch (e: ObjectRetrievalFailureException) {
            throw MarkNotFoundException(id)
        }
    }

    fun getMarkPath(mark: ExistedMark): String {
        val botId = mark.botId
        val id = mark.id
        val fullDirPath = "$dirPath/$botId"
        val path = "$fullDirPath/$id.png"

        return path
    }

}