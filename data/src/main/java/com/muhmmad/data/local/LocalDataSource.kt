package com.muhmmad.data.local

import com.muhmmad.domain.model.Language

interface LocalDataSource {
    suspend fun getToken(): String
    suspend fun getLanguage(): Language
}