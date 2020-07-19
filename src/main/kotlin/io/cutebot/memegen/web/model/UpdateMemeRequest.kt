package io.cutebot.memegen.web.model

import io.cutebot.memegen.domain.meme.model.MemeTextArea
import io.cutebot.memegen.domain.meme.model.UpdateMeme
import io.cutebot.memegen.web.exception.BadRequestWebException
import org.slf4j.LoggerFactory

open class UpdateMemeRequest(
        val alias: String,
        textAreaCoords: String
) {

    val areaSquares: List<MemeTextArea>

    init {
        val areas = textAreaCoords.split(",")
        if (areas.isEmpty() || areas.size % 4  != 0) {
            log.warn("Received wrong value for areas variable: {}", areas)
            throw BadRequestWebException("Count of coordinates must be positive number multiple by 4")
        }

        var i = 0
        val areaList = ArrayList<MemeTextArea>()
        try {
            while (i < (areas.size - 1)) {
                val sq = areas.subList(i, i + 4)
                areaList.add(MemeTextArea(
                        left = sq[0].toBigDecimal(),
                        top = sq[1].toBigDecimal(),
                        right = sq[2].toBigDecimal(),
                        bottom = sq[3].toBigDecimal()
                ))
                i += 4;
            }
        } catch (e: NumberFormatException) {
            throw BadRequestWebException("Wrong decimal values: " + e.message)
        }

        areaSquares = areaList
    }

    fun getUpdateModel(): UpdateMeme {
        return UpdateMeme(
                alias = alias,
                textAreas = areaSquares
        )
    }

    companion object {
        private val log = LoggerFactory.getLogger(CreateMemeRequest::class.java)
    }

}
