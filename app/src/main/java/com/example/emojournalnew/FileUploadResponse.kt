package com.example.emojournal2

import com.google.gson.annotations.SerializedName

data class FileUploadResponse(
    @field:SerializedName("url")
    val url: String,

    @field:SerializedName("message")
    val message: String
)
