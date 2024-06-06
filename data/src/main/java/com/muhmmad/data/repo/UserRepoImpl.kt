package com.muhmmad.data.repo

import com.muhmmad.data.utils.checkResponse
import com.muhmmad.domain.local.LocalDataSource
import com.muhmmad.domain.model.AppMode
import com.muhmmad.domain.model.BaseResponse
import com.muhmmad.domain.model.Card
import com.muhmmad.domain.model.NetworkResponse
import com.muhmmad.domain.model.NotificationsResponse
import com.muhmmad.domain.model.User
import com.muhmmad.domain.remote.GraphQlDataSource
import com.muhmmad.domain.remote.RetrofitDataSource
import com.muhmmad.domain.repo.UserRepo
import okhttp3.MultipartBody

class UserRepoImpl(
    private val graphQlDataSource: GraphQlDataSource,
    private val retrofitDataSource: RetrofitDataSource,
    private val localDataSource: LocalDataSource
) : UserRepo {
    override suspend fun getUserInfo(token: String): NetworkResponse<User> =
        graphQlDataSource.getUserInfo(token)

    override suspend fun saveUserData(user: User) = localDataSource.saveUserData(user)

    override suspend fun uploadUserAvatar(
        token: String,
        image: MultipartBody.Part
    ): NetworkResponse<Any> = retrofitDataSource.uploadUserAvatar(token, image).checkResponse()

    override suspend fun updateUserName(token: String, name: String): NetworkResponse<User> =
        graphQlDataSource.updateUserName(token, name)

    override suspend fun updateUserBio(token: String, bio: String): NetworkResponse<User> =
        graphQlDataSource.updateUserBio(token, bio)

    override suspend fun updatePassword(
        token: String,
        oldPassword: String,
        newPassword: String
    ): NetworkResponse<User> = graphQlDataSource.updatePassword(token, oldPassword, newPassword)

    override suspend fun getAuthorInfo(userId: String, token: String): NetworkResponse<User> =
        graphQlDataSource.getAuthorInfo(userId, token)

    override suspend fun isUserProfile(userId: String): Boolean =
        localDataSource.isUserProfile(userId)

    override suspend fun getUserId(): String = localDataSource.getUserId()
    override suspend fun followUser(token: String, userId: String): NetworkResponse<BaseResponse> =
        graphQlDataSource.followUser(token, userId)

    override suspend fun changeMode(mode: AppMode) = localDataSource.changeMode(mode)
    override suspend fun getUiMode(): AppMode = localDataSource.getUiMode()
    override suspend fun getNotifications(
        token: String,
        page: Int,
        limit: Int
    ): NetworkResponse<NotificationsResponse> =
        graphQlDataSource.getNotifications(token, page, limit)

    override suspend fun getPaymentCards(): List<Card> = localDataSource.getPaymentCards()
    override suspend fun deletePaymentCard(id: Int) = localDataSource.deletePaymentCard(id)
}