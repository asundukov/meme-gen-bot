package io.cutebot.telegram.tgmodel.keyboard;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TgInlineKeyboardButton {
    @JsonProperty("text")
    public String text;

    @JsonProperty("url")
    public String url;

    @JsonProperty("callback_data")
    public String callbackData;

    @JsonProperty("switch_inline_query")
    public String switchInlineQuery;

    @Override
    public String toString() {
        return "TgInlineKeyboardButton{" +
                "text='" + text + '\'' +
                ", url='" + url + '\'' +
                ", callbackData='" + callbackData + '\'' +
                '}';
    }
}
