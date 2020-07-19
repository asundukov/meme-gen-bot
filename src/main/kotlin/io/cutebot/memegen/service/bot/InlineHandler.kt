package io.cutebot.memegen.service.bot

import io.cutebot.memegen.domain.meme.model.ExistedMeme
import io.cutebot.memegen.service.manage.MemeManageService
import io.cutebot.telegram.client.model.inline.TgAnswerInlineQuery
import io.cutebot.telegram.client.model.inline.TgInlineQuery
import io.cutebot.telegram.client.model.inline.TgInlineQueryResultArticle
import io.cutebot.telegram.client.model.inline.TgInlineQueryResultDocument
import io.cutebot.telegram.client.model.inline.TgInlineQueryResultPhoto
import io.cutebot.telegram.client.model.inline.TgInputMessageContent
import org.apache.commons.codec.digest.DigestUtils.md5Hex
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.net.URLEncoder
import java.nio.charset.Charset
import kotlin.math.min

@Service
class InlineHandler(
        private val memeManageService: MemeManageService,
        @Value("\${meme.image-url}")
        private val imageUrl: String,
        @Value("\${bot.inline-result-count}")
        private val inlineResultItemsCount: Int
) {

    fun handle(botId: Int, inlineQuery: TgInlineQuery): TgAnswerInlineQuery {
        val query = inlineQuery.query
        val queryId = inlineQuery.id
        val offset = parseOffset(inlineQuery.offset)

        var (alias, queryText) = parseQuery(query)
        var memes = memeManageService.findByBotAndAliasLike(botId, alias)

        if (memes.isEmpty()) {
            queryText = inlineQuery.query
            memes = memeManageService.getAllActive(botId)
        }

        memes = sublistByOffset(memes, offset)


        if (memes.isEmpty()) {
            val answer = TgAnswerInlineQuery(inlineQueryId = queryId)
            if (offset == 0) {
                answer.results.add(TgInlineQueryResultArticle(
                        id = "no_results",
                        title = "No results",
                        description = "Selecting this I send hi message to current chat",
                        inputMessageContent = TgInputMessageContent(messageText = "Hi there. I can help to make some fun memes")
                ))
            }
            return answer
        } else {
            val answer = TgAnswerInlineQuery(inlineQueryId = queryId, nextOffset = (offset+1).toString())
            if (queryText.isEmpty()) {
                memes.forEach { answer.results.add(getInlineQueryResultDocument(it)) }
            } else {
                memes.forEach { answer.results.add(getInlineQueryResultPhoto(it, queryText)) }
            }
            return answer
        }

    }

    private fun sublistByOffset(memes: List<ExistedMeme>, offset: Int): List<ExistedMeme> {
        if (memes.isEmpty()) {
            return memes
        }
        val from = inlineResultItemsCount * offset
        if (from >= memes.size) {
            return emptyList()
        }
        val to = min(from + inlineResultItemsCount, memes.size)
        return memes.subList(from, to)
    }

    private fun parseOffset(offset: String): Int {
        if (offset.isEmpty()) {
            return 0
        }
        return try {
            offset.toInt()
        } catch (e: NumberFormatException) {
            0
        }
    }

    private fun parseQuery(query: String): ParsedQuery {
        val q = query.trim()
        if (q.isEmpty()) {
            return ParsedQuery("", "")
        }

        var divider = q.indexOf(" ")
        if (divider == -1) {
            return ParsedQuery("", query)
        }

        val queryText = q.substring(divider + 1, q.length).trim()

        val alias = q.substring(0, divider).trim()

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
        val description = "$alias text1 | text 2 | text 3 | ..."
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
