package io.cutebot.telegram.tgmodel

import com.fasterxml.jackson.annotation.JsonProperty

class TgFile(
        @JsonProperty("file_id")
        var fileId: String,

        @JsonProperty("file_unique_id")
        var fileUniqueId: String,

        @JsonProperty("file_size")
        var fileSize: Int? = null,

        @JsonProperty("file_path")
        var filePath: String
)
