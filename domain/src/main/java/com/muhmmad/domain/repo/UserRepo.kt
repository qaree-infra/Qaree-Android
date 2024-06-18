package com.muhmmad.domain.repo

import com.muhmmad.domain.model.AppMode
import com.muhmmad.domain.model.BaseResponse
import com.muhmmad.domain.model.Card
import com.muhmmad.domain.model.Language
import com.muhmmad.domain.model.NetworkResponse
import com.muhmmad.domain.model.NotificationsResponse
import com.muhmmad.domain.model.User
import okhttp3.MultipartBody

interface UserRepo {
    suspend fun getUserInfo(token: String): NetworkResponse<User>
    suspend fun saveUserData(user: User)
    suspend fun uploadUserAvatar(token: String, image: MultipartBody.Part): NetworkResponse<Any>
    suspend fun updateUserName(token: String, name: String): NetworkResponse<User>
    suspend fun updateUserBio(token: String, bio: String): NetworkResponse<User>
    suspend fun updatePassword(
        token: String,
        oldPassword: String,
        newPassword: String
    ): NetworkResponse<User>

    suspend fun getAuthorInfo(userId: String, token: String): NetworkResponse<User>
    suspend fun isUserProfile(userId: String): Boolean
    suspend fun getUserId(): String
    suspend fun followUser(token: String, userId: String): NetworkResponse<BaseResponse>
    suspend fun changeMode(mode: AppMode)
    suspend fun getUiMode(): AppMode
    suspend fun getLanguage(): Language
    suspend fun changeLanguage(lang: Language)
    suspend fun getNotifications(
        token: String,
        page: Int,
        limit: Int
    ): NetworkResponse<NotificationsResponse>

    suspend fun getPaymentCards(): List<Card>
    suspend fun deletePaymentCard(card: Card)
    suspend fun addPaymentCard(card: Card)
}