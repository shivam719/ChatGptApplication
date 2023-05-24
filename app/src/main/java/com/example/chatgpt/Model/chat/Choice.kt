package com.example.chatgpt.Model.chat

data class Choice(
    val finish_reason: String? = "",
    val index: Int? = 0,
    val logprobs: Any? = Any(),
    val text: String? = ""
)