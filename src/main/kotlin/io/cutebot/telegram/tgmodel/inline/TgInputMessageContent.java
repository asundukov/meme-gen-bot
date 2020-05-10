package io.cutebot.telegram.tgmodel.inline;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TgInputMessageContent {
    @JsonProperty("message_text")
    public String messageText;
    @JsonProperty("parse_mode")
    public String parseMode = "HTML";
    @JsonProperty("disable_web_page_preview")
    public Boolean disableWebPagePreview = true;

    @Override
    public String toString() {
        return "TgInputMessageContent{" +
                "messageText='" + messageText + '\'' +
                ", parseMode='" + parseMode + '\'' +
                ", disableWebPagePreview=" + disableWebPagePreview +
                '}';
    }
}
