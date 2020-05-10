package io.cutebot.telegram.tgmodel

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class TgDocument (
    @JsonProperty("file_id")
    val fileId: String,

    @JsonProperty("file_unique_id")
    val fileUniqueId: String,

    @JsonProperty("file_size")
    val fileSize: Int,

    @JsonProperty("file_name")
    val fileName: String,

    @JsonProperty("mime_type")
    val mimeType: String

)
