package com.example.chatgpt.Model

data class ImageGenerateRequest(
    val n: Int? = 0,
    val prompt: String? = "",
    val size: String? = ""
)