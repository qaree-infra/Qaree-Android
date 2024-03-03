package com.muhmmad.domain.local

import com.muhmmad.domain.model.Language

interface LocalDataSource {
    suspend fun setToken(token: String)
    suspend fun getToken(): String
    suspend fun getLanguage(): Language
}