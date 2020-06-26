package io.cutebot.telegram

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.cutebot.telegram.exception.TgBotNotFoundException
import io.cutebot.telegram.handlers.SetWebHookDto
import io.cutebot.telegram.tgmodel.TgChat
import io.cutebot.telegram.tgmodel.TgChatAction
import io.cutebot.telegram.tgmodel.TgFile
import io.cutebot.telegram.tgmodel.TgMessage
import io.cutebot.telegram.tgmodel.TgResponseUpdate
import io.cutebot.telegram.tgmodel.TgSendAnimation
import io.cutebot.telegram.tgmodel.TgSendDocument
import io.cutebot.telegram.tgmodel.TgSendPhoto
import io.cutebot.telegram.tgmodel.TgSendTextMessage
import io.cutebot.telegram.tgmodel.TgUser
import io.cutebot.telegram.tgmodel.inline.TgAnswerInlineQuery
import io.cutebot.telegram.tgmodel.response.TgResponseChat
import io.cutebot.telegram.tgmodel.response.TgResponseFile
import io.cutebot.telegram.tgmodel.response.TgResponseMessage
import io.cutebot.telegram.tgmodel.response.TgResponseUser
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.ContentType.TEXT_PLAIN
import org.apache.http.entity.mime.MultipartEntity
import org.apache.http.entity.mime.content.FileBody
import org.apache.http.entity.mime.content.StringBody
import org.apache.http.impl.client.DefaultHttpClient
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate


@Service
class TelegramService(
        @Value("\${telegram.webhook.url}")
        private val webHookUrl: String,

        private val telegramRestTemplate: RestTemplate,
        private val telegramFileRestTemplate: RestTemplate
) {

    fun getMe(token: String): TgUser {
        return getMethod(token, "getMe", TgResponseUser::class.java).result!!
    }

    fun getChat(token: String, id: Long): TgChat {
        return getMethod(token, "getChat?chat_id=$id", TgResponseChat::class.java).result!!
    }

    fun getFile(token: String, fileId: String): TgFile {
        return getMethod(token, "getFile?file_id=$fileId", TgResponseFile::class.java).result!!
    }

    fun getUpdates(token: String, offset: Int, limit: Int, timeout: Int): TgResponseUpdate {
        val method = "getUpdates?offset=$offset&limit=$limit&timeout=$timeout"
        return try {
            getMethod(token, method, TgResponseUpdate::class.java)
        } catch (e: HttpClientErrorException) {
            if (e.rawStatusCode == 401) {
                throw TgBotNotFoundException()
            }
            throw RuntimeException(e)
        }
    }

    fun sendPhoto(token: String, sendPhoto: TgSendPhoto): TgMessage {
        val url = getUrl(token, "sendPhoto")
        val reqEntity = MultipartEntity()
        reqEntity.addPart("photo", FileBody(sendPhoto.photo))
        reqEntity.addPart("chat_id", StringBody(sendPhoto.chatId.toString(), TEXT_PLAIN))
        sendPhoto.caption?.let {
            reqEntity.addPart("caption", StringBody(it, TEXT_PLAIN))
        }

        return postMultipartData(reqEntity, url)
    }

    fun sendDocument(token: String, sendPhoto: TgSendDocument): TgMessage {
        val url = getUrl(token, "sendDocument")
        val reqEntity = MultipartEntity()

        reqEntity.addPart("document", FileBody(sendPhoto.document))
        reqEntity.addPart("chat_id", StringBody(sendPhoto.chatId.toString()))
        return postMultipartData(reqEntity, url)
    }

    private fun postMultipartData(entity: MultipartEntity, url: String): TgMessage {
        val httpclient: HttpClient = DefaultHttpClient()
        val httpPost = HttpPost(url)
        httpPost.setEntity(entity)

        val response = httpclient.execute(httpPost)

        val r = String(response.entity.content.readAllBytes())

        return jacksonObjectMapper()
                .readerFor(TgResponseMessage::class.java)
                .readValue(r, TgResponseMessage::class.java).result!!

    }


    fun sendAnimation(token: String, animation: TgSendAnimation): TgMessage {
        return postMethod(token, animation, "sendAnimation", TgResponseMessage::class.java).result!!
    }

    fun sendMessage(token: String, sendMessage: TgSendTextMessage): TgMessage {
        return try {
            postMethod(token, sendMessage, "sendMessage", TgResponseMessage::class.java).result!!
        } catch (e: HttpClientErrorException) {
            log.warn("error during sendMessage", e)
            if (e.rawStatusCode == 403) {
                log.info("Blocked by user: {}", e.responseBodyAsString)
            }
            if (e.rawStatusCode == 400) {
                log.info("Chat not found: {}", e.responseBodyAsString)
            }
            throw e
        }
    }

    fun sendChatAction(token: String, chatId: Long, action: String) {
        val chatAction = TgChatAction(chatId, action)
        postMethod(token, chatAction, "sendChatAction", String::class.java)
    }

    fun updateMessage(botToken: String, tgSendMessage: TgSendTextMessage, updateMessageId: Long?): TgMessage {
        tgSendMessage.messageId = updateMessageId
        return try {
            postMethod(botToken, tgSendMessage, "editMessageText", TgResponseMessage::class.java).result!!
        } catch (e: HttpClientErrorException) {
            log.warn("error during editMessageText", e)
            if (e.rawStatusCode == 403) {
                log.info("Blocked by user: {}", e.responseBodyAsString)
            }
            if (e.rawStatusCode == 400) {
                log.info("Chat not found: {}", e.responseBodyAsString)
            }
            throw e
        }
    }

    fun answerInlineQuery(token: String, tgAnswerInlineQuery: TgAnswerInlineQuery) {
        try {
            val resp = postMethod(token, tgAnswerInlineQuery, "answerInlineQuery", String::class.java)
        } catch (e: HttpClientErrorException) {
            log.warn("error during answerInlineQuery", e)
            if (e.rawStatusCode == 403) {
                log.info("Blocked by user: {}", e.responseBodyAsString)
            }
            if (e.rawStatusCode == 400) {
                log.info("Chat not found: {}", e.responseBodyAsString)
            }
            throw e
        }
    }

    fun setWebHook(token: String) {
        val setWebHookDto = SetWebHookDto("$webHookUrl/webhook/$token")
        postMethod(token, setWebHookDto, "setWebhook", Void::class.java)
    }

    fun deleteWebhook(token: String) {
        getMethod(token, "deleteWebhook", Void::class.java)
    }

    fun getDownloadUrl(token: String, filePath: String): String {
        return "https://api.telegram.org/file/bot$token/$filePath"
    }

    fun downloadFile(token: String, fileId: String): String {
        val url = getDownloadUrl(token, fileId)
        log.info("GET {}", url)
        return try {
            val response = telegramFileRestTemplate.getForEntity(url, String::class.java).body
            log.info("GET {} RESPONSE {} bytes", url, response!!.length.toString())
            response!!
        } catch (e: HttpClientErrorException) {
            log.info("Error telegram api. GET {}. Response {}", url, e.responseBodyAsString)
            throw e
        }
    }

    private fun <T> postMethod(token: String, request: Any, methodName: String, clazz: Class<T>): T {
        val url = getUrl(token, methodName)
        log.info("POST {} to {}", request, url)
        return try {
            val response = telegramRestTemplate.postForEntity(url, request, clazz).body
            log.info("POST {} RESPONSE {}", methodName, response)
            response!!
        } catch (e: HttpClientErrorException) {
            log.info(e.responseBodyAsString)
            throw e
        }
    }

    private fun <T> getMethod(token: String, methodName: String, clazz: Class<T>): T {
        val url = getUrl(token, methodName)
        return try {
            val response = telegramRestTemplate.getForEntity(url, clazz).body
            response!!
        } catch (e: HttpClientErrorException) {
            log.info("Error telegram api. GET {}. Response {}", url, e.responseBodyAsString)
            throw e
        }
    }

    private fun getUrl(token: String, methodName: String): String {
        val url = "https://api.telegram.org/bot$token/$methodName"
        return url
    }


    companion object {
        private val log = LoggerFactory.getLogger(TelegramService::class.java)
    }

}
