package com.example.chatgpt.Api

import com.example.chatgpt.Model.Image.ImageGenerateModel
import com.example.chatgpt.Model.ImageGenerateRequest
import com.example.chatgpt.Model.chat.ChatModel
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import java.lang.reflect.Type

interface ApiInterFace {
    @POST("/v1/images/generations")
    suspend fun generateImage( @Header("Content-Type") contentType: String,
                               @Header("Authorization") authorization: String,
                               @Body requestBody: RequestBody): ImageGenerateModel

    @POST("/v1/completions")
    suspend fun generateChat(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Body requestBody: RequestBody
    ): ChatModel
}