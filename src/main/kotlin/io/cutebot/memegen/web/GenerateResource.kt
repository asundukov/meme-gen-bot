package io.cutebot.memegen.web

import io.cutebot.memegen.service.ImageGenerator
import io.cutebot.memegen.service.messagehandlers.inline.InlineHandler
import io.cutebot.memegen.service.model.ImageSize
import io.cutebot.memegen.service.model.ImageSize.IMAGE
import io.cutebot.memegen.service.model.ImageSize.THUMB
import io.cutebot.memegen.web.exception.NotFoundWebException
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.util.FileCopyUtils.copyToByteArray
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.io.File
import java.io.FileNotFoundException

@Controller
@RequestMapping(
        path = ["/meme/{memeId}"],
        produces = [MediaType.IMAGE_JPEG_VALUE]
)
class GenerateResource(
        private val imageGenerator: ImageGenerator
) {
    private val log = LoggerFactory.getLogger(InlineHandler::class.java)

    @GetMapping("/image")
    fun get(
            @PathVariable memeId: Int,
            @RequestParam("q", defaultValue = "") query: String
    ): ResponseEntity<ByteArray> {
        log.info("original request meme {} query {}", memeId, query)
        val filePath = imageGenerator.generate(memeId, query)
        return responseWithImage(filePath)
    }

    @GetMapping("/thumb")
    fun getThumb(
            @PathVariable memeId: Int,
            @RequestParam("q", defaultValue = "") query: String
    ): ResponseEntity<ByteArray> {
        log.info("thumb request meme {} query {}",  memeId, query)
        val filePath = imageGenerator.generateThumb(memeId, query)
        return responseWithImage(filePath)
    }

    private fun responseWithImage(filePath: String): ResponseEntity<ByteArray> {
        try {
            val imgFile = File(filePath)
            val bytes: ByteArray = copyToByteArray(imgFile.inputStream())

            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(bytes)
        } catch (e: FileNotFoundException) {
            log.warn("Not found file {}", filePath)
            throw NotFoundWebException()
        }
    }

}
