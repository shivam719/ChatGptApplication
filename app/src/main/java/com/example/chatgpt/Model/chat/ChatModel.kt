package com.example.chatgpt.Model.chat

data class ChatModel(
    val choices: List<Choice?>? = listOf(),
    val created: Int? = 0,
    val id: String? = "",
    val model: String? = "",
    val `object`: String? = "",
    val usage: Usage? = Usage()
)