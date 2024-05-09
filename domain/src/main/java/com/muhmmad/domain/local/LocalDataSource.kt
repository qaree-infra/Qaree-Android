package com.muhmmad.domain.local

import com.muhmmad.domain.model.Language
import com.muhmmad.domain.model.User

interface LocalDataSource {
    suspend fun setFirstTime(isFirstTime: Boolean)
    suspend fun isFirstTime(): Boolean
    suspend fun setToken(token: String)
    suspend fun getToken(): String
    suspend fun saveUserData(user: User)
    suspend fun getLanguage(): Language
    suspend fun isUserProfile(userId: String): Boolean
}