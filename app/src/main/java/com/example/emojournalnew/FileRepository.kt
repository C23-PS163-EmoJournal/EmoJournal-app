package com.example.emojournalnew

import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okio.IOException
import retrofit2.HttpException
import java.io.File

class FileRepository {

    suspend fun uploadAudio(file: File): Boolean {
        return try {
            FileApi.instance.uploadAudio(
                audio = MultipartBody.Part
                    .createFormData(
                        "audio",
                        file.name,
                        file.asRequestBody()
                    )
            )
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        } catch (e: HttpException) {
            e.printStackTrace()
            false
        }
    }
}