package io.cutebot.telegram.tgmodel.photo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TgPhoto {
    @JsonProperty("small_file_id")
    public String smallFileId;

    @JsonProperty("small_file_unique_id")
    public String smallFileUniqueId;

    @JsonProperty("big_file_id")
    public String bigFileId;

    @JsonProperty("big_file_unique_id")
    public String bigFileUniqueId;
}
