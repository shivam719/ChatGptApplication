package com.example.chatgpt.Model

data class ChatRequest(
    val max_tokens: Int? = 0,
    val model: String? = "",
    val prompt: String? = "",
    val temperature: Double? = 0.0
)