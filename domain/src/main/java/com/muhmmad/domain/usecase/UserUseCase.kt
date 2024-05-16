package com.muhmmad.domain.usecase

import com.muhmmad.domain.model.User
import com.muhmmad.domain.repo.UserRepo
import okhttp3.MultipartBody

class UserUseCase(private val repo: UserRepo) {
    suspend fun getUserInfo(token: String) = repo.getUserInfo(token)
    suspend fun saveUserData(user: User) = repo.saveUserData(user)
    suspend fun uploadUserAvatar(token: String, image: MultipartBody.Part) =
        repo.uploadUserAvatar(token, image)

    suspend fun updateUserName(token: String, name: String) = repo.updateUserName(token, name)
    suspend fun updateUserBio(token: String, bio: String) = repo.updateUserBio(token, bio)
    suspend fun updatePassword(
        token: String,
        oldPassword: String,
        newPassword: String
    ) = repo.updatePassword(token, oldPassword, newPassword)

    suspend fun getAuthorInfo(userId: String, token: String) = repo.getAuthorInfo(userId, token)
    suspend fun isUserProfile(userId: String) = repo.isUserProfile(userId)
    suspend fun getUserId() = repo.getUserId()
    suspend fun followUser(token: String, userId: String) = repo.followUser(token, userId)
}