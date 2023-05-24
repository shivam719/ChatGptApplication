package com.example.chatgpt.Api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object ApiUtilities {

    fun getApiInterFace(): ApiInterFace {
        return Retrofit.Builder()
            .baseUrl("https://api.openai.com")
            .client(
                OkHttpClient().newBuilder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .readTimeout(timeout = 10000L, TimeUnit.SECONDS)
                    .writeTimeout(timeout = 10000L, TimeUnit.SECONDS)
                    .connectTimeout(timeout = 10000L, TimeUnit.SECONDS).build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiInterFace::class.java)
    }
}