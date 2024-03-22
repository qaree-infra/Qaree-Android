package com.muhmmad.domain.remote

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface RetrofitDataSource {
    @Multipart
    @POST("upload/user/avatar")
    suspend fun uploadUserAvatar(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part,
    ): Response<Any>
}