package com.example.emojournalnew

import okhttp3.MultipartBody
import retrofit2.Retrofit
import retrofit2.http.Multipart
import retrofit2.http.Part

interface FileApi {

    @Multipart
    suspend fun uploadAudio(
        @Part audio: MultipartBody.Part

    )

    companion object {
        val instance by lazy {
            Retrofit.Builder()
                .baseUrl("https://backend-api-3u667offna-uc.a.run.app/")
                .build()
                .create(FileApi::class.java)
        }
    }
}