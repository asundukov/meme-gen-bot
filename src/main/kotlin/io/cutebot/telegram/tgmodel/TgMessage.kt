package io.cutebot.telegram.tgmodel

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import io.cutebot.telegram.tgmodel.photo.TgPhotoSize

@JsonIgnoreProperties(ignoreUnknown = true)
class TgMessage {
    @field: JsonProperty("message_id")
    var messageId: Long? = null

    @field: JsonProperty
    var from: TgUser? = null

    @field: JsonProperty
    var chat: TgChatShort? = null

    @field: JsonProperty
    var date: Long? = null

    @field: JsonProperty("forward_from")
    var forwardFrom: TgUser? = null

    @field: JsonProperty("forward_from_chat")
    var forwardFromChat: TgChatShort? = null

    @field: JsonProperty("forward_from_message_id")
    var forwardFromMessageId: Long? = null

    @field: JsonProperty("forward_signature")
    var forwardSignature: Long? = null

    @field: JsonProperty("forward_date")
    var forwardDate: Long? = null

    @field: JsonProperty("reply_to_message")
    var replyToMessage: TgMessage? = null

    @field: JsonProperty("edit_date")
    var editDate: Long? = null

    @field: JsonProperty("media_group_id")
    var mediaGroupId: String? = null

    @field: JsonProperty("author_signature")
    var authorSignature: String? = null

    @field: JsonProperty("text")
    var text: String? = null

    @field: JsonProperty("caption")
    var caption: String? = null

    @field: JsonProperty("photo")
    var photo: List<TgPhotoSize>? = null

    @field: JsonProperty("document")
    var document: TgDocument? = null

}
