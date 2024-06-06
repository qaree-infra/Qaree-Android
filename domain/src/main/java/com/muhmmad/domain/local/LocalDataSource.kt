package com.muhmmad.domain.local

import com.muhmmad.domain.model.AppMode
import com.muhmmad.domain.model.Card
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
    suspend fun logout()
    suspend fun getUserId(): String
    suspend fun changeMode(mode: AppMode)
    suspend fun getUiMode(): AppMode
    suspend fun deleteUserData()
    suspend fun getPaymentCards(): List<Card>
    suspend fun deletePaymentCard(id: Int)
}