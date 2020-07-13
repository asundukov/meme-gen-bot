package io.cutebot.memegen.service.messagehandlers.inline

import io.cutebot.memegen.domain.meme.model.ExistedMeme
import io.cutebot.memegen.service.manage.MemeManageService
import io.cutebot.telegram.TelegramService
import io.cutebot.telegram.handlers.BaseBot
import io.cutebot.telegram.tgmodel.TgUser
import io.cutebot.telegram.tgmodel.inline.TgAnswerInlineQuery
import io.cutebot.telegram.tgmodel.inline.TgInlineQueryResultArticle
import io.cutebot.telegram.tgmodel.inline.TgInlineQueryResultDocument
import io.cutebot.telegram.tgmodel.inline.TgInlineQueryResultPhoto
import io.cutebot.telegram.tgmodel.inline.TgInputMessageContent
import org.apache.commons.codec.digest.DigestUtils.md5Hex
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.net.URLEncoder
import java.nio.charset.Charset

@Service
class InlineHandler(
        private val memeManageService: MemeManageService,
        private val telegramService: TelegramService,
        @Value("\${meme.image-url}")
        private val imageUrl: String
) {

    fun handle(bot: BaseBot, query: String, user: TgUser, queryId: String) {
        val (alias, queryText) = parseQuery(query)
        var memes = memeManageService.findByBotAndAliasLike(bot.id, alias)
        if (memes.size > 3) {
            memes = memes.subList(0, 3)
        }

        val answer = TgAnswerInlineQuery(queryId)
        if (memes.isEmpty()) {
            answer.results.add(TgInlineQueryResultArticle(
                    id = "no_results",
                    title = "No results",
                    description = "Selecting this I send hi message to current chat",
                    inputMessageContent = TgInputMessageContent(messageText = "Hi there. I can help to make some fun memes")
            ))
        } else {
            if (queryText.isEmpty()) {
                memes.forEach { answer.results.add(getInlineQueryResultDocument(it)) }
            } else {
                memes.forEach { answer.results.add(getInlineQueryResultPhoto(it, queryText)) }
            }
        }
        telegramService.answerInlineQuery(bot.token, answer)
        return
    }

    private fun parseQuery(query: String): ParsedQuery {
        val q = query.trim()
        if (q.isEmpty()) {
            return ParsedQuery("", "")
        }
        if (q[0]!='/') {
            return ParsedQuery("", query)
        }

        var divider = q.indexOf(" ")

        val queryText = if (divider > 0 && divider < q.length) {
            q.substring(divider + 1, q.length)
        } else {
            ""
        }
        divider--
        if (divider < 0) divider = q.length

        val alias = q.substring(1, divider)

        return ParsedQuery(alias, queryText)
    }

    data class ParsedQuery(
            val filter: String,
            val query: String
    )

    private fun getInlineQueryResultPhoto(meme: ExistedMeme, queryText: String): TgInlineQueryResultPhoto {
        val memeId = meme.id
        val encodedQuery = URLEncoder.encode(queryText, Charset.defaultCharset())
        val resultId = "$memeId$encodedQuery"

        return TgInlineQueryResultPhoto(
                id = md5Hex("photo$resultId"),
                photoUrl = "$imageUrl/meme/$memeId/image?q=$encodedQuery",
                thumbUrl = "$imageUrl/meme/$memeId/thumb?q=$encodedQuery"
        )
    }

    private fun getInlineQueryResultDocument(meme: ExistedMeme): TgInlineQueryResultDocument {
        val memeId = meme.id
        val resultId = "${memeId}_thumb"
        val areaCount = meme.areas.size
        val title = "$areaCount total text areas"
        val alias = meme.alias
        val description = "/$alias text1 | text 2 | text 3 | ..."
        return TgInlineQueryResultDocument(
                id = md5Hex("doc$resultId"),
                documentUrl = "$imageUrl/meme/$memeId/image",
                thumbUrl = "$imageUrl/meme/$memeId/thumb",
                mimeType = "application/pdf",
                title = title,
                description = description
        )
    }

}
