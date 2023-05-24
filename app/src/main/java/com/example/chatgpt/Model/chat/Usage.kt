package com.example.chatgpt.Model.chat

data class Usage(
    val completion_tokens: Int? = 0,
    val prompt_tokens: Int? = 0,
    val total_tokens: Int? = 0
)