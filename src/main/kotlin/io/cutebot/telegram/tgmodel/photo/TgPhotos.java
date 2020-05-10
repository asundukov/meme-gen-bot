package io.cutebot.telegram.tgmodel.photo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TgPhotos {
    @JsonProperty("total_count")
    public Long totalCount;

    public List<TgPhotoList> photos;

    @Override
    public String toString() {
        return "TgPhotos{" +
                "totalCount=" + totalCount +
                ", photoLists=" + photos +
                '}';
    }

    public TgPhotoInfo getFirstSmall() {
        return photos.get(0).stream()
                .min(TgPhotoInfo::compareTo).orElse(null);
    }

}
