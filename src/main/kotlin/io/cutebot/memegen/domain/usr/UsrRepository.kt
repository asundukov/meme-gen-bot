package io.cutebot.memegen.domain.usr

import io.cutebot.memegen.domain.usr.model.UsrEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UsrRepository: JpaRepository<UsrEntity, Long>