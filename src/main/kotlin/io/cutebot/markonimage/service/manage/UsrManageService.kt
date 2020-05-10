package io.cutebot.markonimage.service.manage

import io.cutebot.markonimage.domain.usr.UsrRepository
import io.cutebot.markonimage.domain.usr.model.ExistedUsr
import io.cutebot.markonimage.domain.usr.model.UsrEntity
import io.cutebot.telegram.tgmodel.TgUser
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.Calendar

@Service
class UsrManageService(
        private val usrRepository: UsrRepository
) {

    fun getExistedUsr(usr: TgUser): ExistedUsr {
        return ExistedUsr(getOrCreateById(usr))
    }

    internal fun getOrCreateById(user: TgUser): UsrEntity {
        val existed = usrRepository.findByIdOrNull(user.id)
        existed?.let { return it }

        val newEntity = UsrEntity(
                usrId = user.id,
                userName = user.userName ?: "",
                firstName = user.firstName ?: "",
                lastName = user.lastName ?: "",
                createdOn = Calendar.getInstance(),
                languageCode = user.languageCode ?: ""
        )
        usrRepository.save(newEntity)
        return newEntity
    }

}