package com.muhmmad.data.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.muhmmad.domain.remote.RetrofitDataSource
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private val gson: Gson by lazy {
        GsonBuilder()
            .setLenient()
            .create()
    }
    private val httpClient: OkHttpClient.Builder by lazy {
        OkHttpClient.Builder()
            .callTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .followRedirects(false)
            .followSslRedirects(true)
            .writeTimeout(60, TimeUnit.SECONDS)
    }

    fun retrofitDataSource(BASE_URL: String): RetrofitDataSource {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient.build())
            .build().create(RetrofitDataSource::class.java)
    }
}