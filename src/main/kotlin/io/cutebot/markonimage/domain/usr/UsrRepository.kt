package io.cutebot.markonimage.domain.usr

import io.cutebot.markonimage.domain.usr.model.UsrEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UsrRepository: JpaRepository<UsrEntity, Long>