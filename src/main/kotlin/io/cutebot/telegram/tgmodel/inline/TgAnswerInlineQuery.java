package io.cutebot.telegram.tgmodel.inline;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TgAnswerInlineQuery {
    @JsonProperty("inline_query_id")
    public String inlineQueryId;

    public List<TgInlineQueryResult> results = new ArrayList<>();

    @JsonProperty("cache_time")
    public Integer cacheTime = 15;

    @JsonProperty("is_personal")
    public Boolean isPersonal = true;

    @JsonProperty("next_offset")
    public String nextOffset = "";

    @JsonProperty("switch_pm_text")
    public String switchPmText;

    @JsonProperty("switch_pm_parameter")
    public String switchPmParameter;

    @Override
    public String toString() {
        return "TgAnswerInlineQuery{" +
                "inlineQueryId='" + inlineQueryId + '\'' +
                ", results=" + results +
                ", cacheTime=" + cacheTime +
                ", isPersonal=" + isPersonal +
                ", nextOffset='" + nextOffset + '\'' +
                ", switchPmText='" + switchPmText + '\'' +
                ", switchPmParameter='" + switchPmParameter + '\'' +
                '}';
    }
}
