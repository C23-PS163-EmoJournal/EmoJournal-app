package com.example.emojournal2

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("/upload")
    fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("file") body: RequestBody,
    ): Call<ResponseBody>

}