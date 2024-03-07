package com.muhmmad.domain.local

import com.muhmmad.domain.model.Language

interface LocalDataSource {
    suspend fun setFirstTime(isFirstTime: Boolean)
    suspend fun isFirstTime(): Boolean
    suspend fun setToken(token: String)
    suspend fun getToken(): String
    suspend fun getLanguage(): Language
}