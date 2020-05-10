package io.cutebot.telegram.tgmodel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TgChosenInlineResult {
    @JsonProperty("result_id")
    public String resultId;

    public TgUser from;

    @JsonProperty("inline_message_id")
    public String inlineMessageId;

    public String query;

    @Override
    public String toString() {
        return "TgChosenInlineResult{" +
                "resultId='" + resultId + '\'' +
                ", from=" + from +
                ", inlineMessageId='" + inlineMessageId + '\'' +
                ", query='" + query + '\'' +
                '}';
    }
}
