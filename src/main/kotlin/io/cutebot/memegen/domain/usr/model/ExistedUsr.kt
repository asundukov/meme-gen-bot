package io.cutebot.memegen.domain.usr.model

class ExistedUsr(entity: UsrEntity) {
    val id = entity.usrId
    val languageCode = entity.languageCode
}
