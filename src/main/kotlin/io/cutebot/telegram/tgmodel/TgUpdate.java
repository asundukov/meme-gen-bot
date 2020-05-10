package io.cutebot.telegram.tgmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.cutebot.telegram.tgmodel.inline.TgInlineQuery;

public class TgUpdate {

    @JsonProperty("update_id")
    public Integer updateId;

    public TgMessage message;

    @JsonProperty("edited_message")
    public TgMessage editedMessage;

    @JsonProperty("channel_post")
    public TgMessage channelPost;

    @JsonProperty("inline_query")
    public TgInlineQuery inlineQuery;

    @JsonProperty("callback_query")
    public TgCallbackQuery callbackQuery;

    @JsonProperty("chosen_inline_result")
    public TgChosenInlineResult chosenInlineResult;

    @Override
    public String toString() {
        return "TgUpdate{" +
                "updateId=" + updateId +
                ", message=" + message +
                ", editedMessage=" + editedMessage +
                ", channelPost=" + channelPost +
                ", inlineQuery=" + inlineQuery +
                ", callbackQuery=" + callbackQuery +
                ", chosenInlineResult=" + chosenInlineResult +
                '}';
    }
}
