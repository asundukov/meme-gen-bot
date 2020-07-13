package io.cutebot.memegen.service

import io.cutebot.memegen.service.manage.BotManageService
import io.cutebot.memegen.service.messagehandlers.AboutMessageHandler
import io.cutebot.memegen.service.messagehandlers.HelpMessageHandler
import io.cutebot.memegen.service.messagehandlers.MessageHandler
import io.cutebot.memegen.service.messagehandlers.StartMessageHandler
import io.cutebot.memegen.service.messagehandlers.UnknownMessageHandler
import io.cutebot.memegen.service.messagehandlers.inline.InlineHandler
import io.cutebot.telegram.TelegramService
import io.cutebot.telegram.handlers.BaseBot
import io.cutebot.telegram.handlers.TgBotLongPollHandler
import io.cutebot.telegram.handlers.TgBotWebHookHandler
import io.cutebot.telegram.tgmodel.TgBotCommand
import io.cutebot.telegram.tgmodel.TgBotCommands
import io.cutebot.telegram.tgmodel.TgResponseUpdate
import io.cutebot.telegram.tgmodel.TgSendTextMessage
import io.cutebot.telegram.tgmodel.TgUpdate
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import kotlin.math.min

@Service
class BotHandleService(
        private val telegramService: TelegramService,
        private val botManageService: BotManageService,
        helpMessageHandler: HelpMessageHandler,
        private val inlineHandler: InlineHandler
): TgBotWebHookHandler, TgBotLongPollHandler {
    private val messagesMap: Map<String, MessageHandler> = mapOf(
            UNKNOWN_MESSAGE to UnknownMessageHandler(),
            "/start" to StartMessageHandler(),
            "/help" to helpMessageHandler,
            "/about" to AboutMessageHandler()
    )

    override fun handle(token: String, update: TgUpdate) {
        val bot = botManageService.getByToken(token)
        return handle(BaseBot(bot.id, bot.token), update)
    }

    override fun handle(bot: BaseBot, update: TgUpdate) {
        if (update.message != null) {
            val chatId = update.message.chat.id
            val usr = update.message.from!!
            when {
                update.message.text != null -> {
                    val (command, params) = extractCommand(update.message.text)
                    val handler = messagesMap[command] ?: messagesMap[UNKNOWN_MESSAGE]!!
                    val text = handler.handle(bot, params, chatId, usr)
                    if (text.isNotEmpty()) {
                        telegramService.sendMessage(bot.token, TgSendTextMessage(chatId, text))
                    }
                }
            }
        }
        if (update.inlineQuery != null) {
            inlineHandler.handle(bot, update.inlineQuery.query, update.inlineQuery.from, update.inlineQuery.id)
        }

    }

    fun setCommands(bot: BaseBot) {
        val tgBotCommands = TgBotCommands(ArrayList())
        messagesMap.forEach {
            val description = it.value.getCommandDescription(bot)
            if (description != null) {
                tgBotCommands.commands.add(TgBotCommand(it.key, description))
            }
        }
        telegramService.setCommands(bot.token, tgBotCommands)
    }

    private fun extractCommand(text: String): CommandWithParams {
        val txt = text.trim()
        var spaceIndex = txt.indexOf(" ")
        var underscopeIndex = txt.indexOf("_")

        if (spaceIndex == -1) {
            spaceIndex = txt.length
        }

        if (underscopeIndex == -1) {
            underscopeIndex = txt.length
        }

        val minIndex = min(spaceIndex, underscopeIndex)

        val command = txt.subSequence(0, minIndex).toString()
        var params = txt.subSequence(minIndex, txt.length).toString().trim()

        if (params.isNotEmpty() && params[0] == '_') {
            params = params.substring(1, params.length)
        }

        return CommandWithParams(command, params)
    }

    override fun getMessages(botToken: String, offset: Int, timeout: Int, limit: Int): TgResponseUpdate {
        return telegramService.getUpdates(botToken, offset, limit, timeout)
    }

    fun stopWithWebHook(botToken: String) {
        telegramService.deleteWebhook(botToken)
    }

    fun startWithWebHook(botToken: String) {
        telegramService.setWebHook(botToken)
    }

    companion object {
        private val log = LoggerFactory.getLogger(BotHandleService::class.java)
        private val UNKNOWN_MESSAGE = "unknown";
    }

    data class CommandWithParams(
            val command: String,
            val params: String
    )

}
