package io.cutebot.telegram.tgmodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.cutebot.telegram.tgmodel.keyboard.TgKeyboard;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public class TgSendTextMessage extends TgSendMessage {
    public String text;

    @JsonProperty("parse_mode")
    public String parseMode = "HTML";

    @JsonProperty("disable_web_page_preview")
    public boolean disableWebPagePreview = true;

    @JsonProperty("disable_notification")
    public boolean disableNotification = true;

    @JsonProperty("reply_to_message_id")
    public Long replyToMessageId = null;

    @JsonProperty("reply_markup")
    public TgKeyboard replyMarkup = null;

    @JsonProperty("message_id")
    public Long messageId;

    @JsonProperty("inline_message_id")
    public String inlineMessageId;

    public TgSendTextMessage(long chatId) {
        super(chatId);
    }

    @Override
    public String toString() {
        return "TgSendTextMessage{" +
                "text='" + text + '\'' +
                ", parseMode='" + parseMode + '\'' +
                ", disableWebPagePreview=" + disableWebPagePreview +
                ", disableNotification=" + disableNotification +
                ", replyToMessageId=" + replyToMessageId +
                ", replyMarkup=" + replyMarkup +
                ", messageId=" + messageId +
                ", inlineMessageId='" + inlineMessageId + '\'' +
                '}';
    }
}
