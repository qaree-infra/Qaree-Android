package com.muhmmad.data.repo

import com.muhmmad.data.utils.checkResponse
import com.muhmmad.domain.model.NetworkResponse
import com.muhmmad.domain.model.User
import com.muhmmad.domain.remote.GraphQlDataSource
import com.muhmmad.domain.remote.RetrofitDataSource
import com.muhmmad.domain.repo.UserRepo
import okhttp3.MultipartBody

class UserRepoImpl(
    private val graphQlDataSource: GraphQlDataSource,
    private val retrofitDataSource: RetrofitDataSource
) : UserRepo {
    override suspend fun getUserInfo(token: String): NetworkResponse<User> =
        graphQlDataSource.getUserInfo(token)

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
}