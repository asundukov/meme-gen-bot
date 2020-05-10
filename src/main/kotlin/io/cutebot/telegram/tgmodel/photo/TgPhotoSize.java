package io.cutebot.telegram.tgmodel.photo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TgPhotoSize {
    @JsonProperty("file_id")
    public String fileId;

    @JsonProperty("file_unique_id")
    public String fileUniqueId;

    @JsonProperty("width")
    public Integer width;

    @JsonProperty("height")
    public Integer height;

    @JsonProperty("file_size")
    public Integer fileSize;

    @Override
    public String toString() {
        return "TgPhotoSize{" +
                "fileId='" + fileId + '\'' +
                ", fileUniqueId='" + fileUniqueId + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", fileSize=" + fileSize +
                '}';
    }
}
