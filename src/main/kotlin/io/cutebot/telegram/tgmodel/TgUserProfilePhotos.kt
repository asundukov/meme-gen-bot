package io.cutebot.telegram.tgmodel

import com.fasterxml.jackson.annotation.JsonProperty
import io.cutebot.telegram.tgmodel.photo.TgPhotoSize

data class TgUserProfilePhotos (
        @field:JsonProperty(value = "total_count")
        val totalCount: Int,

        val photos: List<List<TgPhotoSize>>
)
