package io.cutebot.telegram.tgmodel.photo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TgPhotoInfo implements Comparable<TgPhotoInfo> {
    @JsonProperty("file_id")
    public String fileId;

    @JsonProperty("file_size")
    public Long fileSize;

    public Long width;

    public Long height;

    @Override
    public String toString() {
        return "TgPhotoInfo{" +
                "fileId='" + fileId + '\'' +
                ", fileSize=" + fileSize +
                ", width=" + width +
                ", height=" + height +
                '}';
    }

    @Override
    public int compareTo(TgPhotoInfo o) {
        return this.width.compareTo(o.width);
    }
}
