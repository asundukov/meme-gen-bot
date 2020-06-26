package io.cutebot.markonimage.service.manage

import io.cutebot.markonimage.domain.bot.BotRepository
import io.cutebot.markonimage.domain.bot.model.BotEntity
import io.cutebot.markonimage.domain.bot.model.ExistedBot
import io.cutebot.markonimage.domain.bot.model.NewBot
import io.cutebot.markonimage.domain.bot.model.UpdateBot
import io.cutebot.markonimage.domain.mark.model.MarkEntity
import io.cutebot.markonimage.service.exception.BotNotFoundException
import io.cutebot.markonimage.service.exception.TokenAlreadyInUseException
import org.springframework.orm.ObjectRetrievalFailureException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Calendar

@Service
class BotManageService(
        private val repository: BotRepository
) {

    @Transactional
    fun save(bot: NewBot): ExistedBot {
        val existed = repository.findByToken(bot.token)
        if (existed != null) {
            throw TokenAlreadyInUseException()
        }
        val newEntity = BotEntity(
                botId = 0,
                token = bot.token,
                adminUsrId = bot.adminUsrId,
                totalImages = 0,
                createdOn = Calendar.getInstance(),
                defaultMark = null,
                title = bot.title
        )
        repository.save(newEntity);

        return ExistedBot(newEntity)
    }

    @Transactional(readOnly = true)
    fun getById(id: Int): ExistedBot {
        return ExistedBot(getExistedById(id))
    }

    @Transactional
    fun updateBot(botId: Int, bot: UpdateBot): ExistedBot {
        val entity = getExistedById(botId)
        entity.title = bot.title
        bot.token?. let { entity.token = it }
        repository.save(entity)
        return ExistedBot(entity)
    }

    @Transactional(readOnly = true)
    fun getByToken(token: String): ExistedBot {
        return ExistedBot(getExistedEntityByToken(token))
    }

    @Transactional(readOnly = true)
    fun getAll(): List<ExistedBot> {
        return repository.findAll().map { item -> ExistedBot(item) }
    }

    internal fun getExistedEntityByToken(token: String): BotEntity {
        return repository.findByToken(token) ?: throw BotNotFoundException(token)
    }

    internal fun getExistedById(id: Int): BotEntity {
        try {
            return repository.getOne(id)
        } catch (e: ObjectRetrievalFailureException) {
            throw BotNotFoundException(id)
        }
    }

    internal fun setDefaultMark(bot: BotEntity, defaultMark: MarkEntity) {
        bot.defaultMark = defaultMark
        repository.save(bot)
    }

    fun getExistedByUserId(userId: Long): List<ExistedBot> {
        return repository.findAllByAdminUsrId(userId).map { ExistedBot (it) }
    }


}
