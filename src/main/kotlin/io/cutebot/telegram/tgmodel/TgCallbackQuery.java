package io.cutebot.telegram.tgmodel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TgCallbackQuery {
    public Long id;
    public TgUser from;
    public TgMessage message;

    @JsonProperty("chat_instance")
    public String chatInstance;

    public String data;

    @Override
    public String toString() {
        return "TgCallbackQuery{" +
                "id=" + id +
                ", from=" + from +
                ", message=" + message +
                ", chatInstance='" + chatInstance + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
