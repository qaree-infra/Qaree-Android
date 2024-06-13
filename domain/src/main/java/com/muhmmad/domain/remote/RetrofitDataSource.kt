package com.muhmmad.domain.remote

import com.muhmmad.domain.model.BookChapter
import com.muhmmad.domain.model.BookContent
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface RetrofitDataSource {
    @Multipart
    @POST("upload/user/avatar")
    suspend fun uploadUserAvatar(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part,
    ): Response<Any>


    @GET("read/{bookId}/{chapter}/json")
    suspend fun getBookChapter(
        @Header("Authorization") token: String,
        @Path("bookId") bookId: String,
        @Path("chapter") chapter: String,
    ): Response<BookChapter>
}