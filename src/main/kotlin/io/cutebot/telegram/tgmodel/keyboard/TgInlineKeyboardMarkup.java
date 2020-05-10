package io.cutebot.telegram.tgmodel.keyboard;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TgInlineKeyboardMarkup extends TgKeyboard {
    @JsonProperty("inline_keyboard")
    public List<List<TgInlineKeyboardButton>> inlineKeyboard;

    @Override
    public String toString() {
        return "TgInlineKeyboardMarkup{" +
                "inlineKeyboard=" + inlineKeyboard +
                '}';
    }
}
