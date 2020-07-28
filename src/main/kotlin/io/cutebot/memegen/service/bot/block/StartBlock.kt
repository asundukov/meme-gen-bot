package io.cutebot.memegen.service.bot.block

import io.cutebot.telegram.bot.block.BotBlock
import io.cutebot.telegram.bot.block.BotTextBlock
import io.cutebot.telegram.bot.model.PhotoMessage
import io.cutebot.telegram.bot.model.TextMessage
import io.cutebot.telegram.interaction.model.ChatAnswer


class StartBlock : BotTextBlock {

    private var tryHere = true

    override fun getAnswer(): ChatAnswer {
       return if (tryHere) {
           ChatAnswer.inlineQueryInvitation(firstMessage,"Try it", defaultQuery)
       } else {
           ChatAnswer.inlineQueryInvitationChatSelection(secondMessage,"Try it in other chat", defaultQuery)
       }
    }

    override fun handlePhoto(message: PhotoMessage): BotBlock {
        tryHere = message.viaBot == null
        return this
    }

    override fun handleText(message: TextMessage): BotBlock {
        tryHere = true
        return this
    }

    companion object {
        private const val firstMessage =
"""
Welcome to meme-gen-bot
You can easy create some funny memes in inline mode
"""

        private const val secondMessage =
"""
Wow! You did it! Now you can try send it in other chat.    
"""
        private const val defaultQuery =
"""
Noooo, it is just nothing | This is awesome!                    
"""
    }
}
