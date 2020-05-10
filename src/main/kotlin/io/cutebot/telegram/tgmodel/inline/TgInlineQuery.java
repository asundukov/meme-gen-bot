package io.cutebot.telegram.tgmodel.inline;

import io.cutebot.telegram.tgmodel.TgUser;

public class TgInlineQuery {
    public String id;
    public TgUser from;
    public String query;
    public String offset;

    @Override
    public String toString() {
        return "TgInlineQuery{" +
                "id='" + id + '\'' +
                ", from=" + from +
                ", query='" + query + '\'' +
                ", offset='" + offset + '\'' +
                '}';
    }
}
