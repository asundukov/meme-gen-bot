package io.cutebot.memegen.web

import io.cutebot.memegen.service.manage.MemeManageService
import io.cutebot.memegen.web.model.CreateMemeRequest
import io.cutebot.memegen.web.model.GetMemeResponse
import io.cutebot.memegen.web.model.UpdateMemeRequest
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid


@RestController
@RequestMapping(
        path = ["/api/manage/memes"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
)
class MemeManageResource(
        private val service: MemeManageService
) {
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun create(
            @Valid @ModelAttribute meme: CreateMemeRequest
    ): GetMemeResponse {
        val createdMeme = service.add(meme.getCreateModel())
        return GetMemeResponse(createdMeme)
    }

    @GetMapping("/{meme_id}")
    fun get(
            @PathVariable("meme_id") markId: Int
    ): GetMemeResponse {
        return GetMemeResponse(service.getById(markId))
    }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], value = ["/{meme_id}"])
    fun update(
            @PathVariable("meme_id") markId: Int,
            @Valid @ModelAttribute meme: UpdateMemeRequest
    ) {
        return service.update(markId, meme.getUpdateModel())
    }

    @DeleteMapping("/{meme_id}")
    fun delete(
            @PathVariable("meme_id") memeId: Int
    ) {
        return service.deleteById(memeId)
    }

    @GetMapping("/findByBot")
    fun getByBot(
            @RequestParam("bot_id") botId: Int
    ): List<GetMemeResponse> {
        return service.getAllActive(botId).map { GetMemeResponse(it) }
    }

}