package com.muhmmad.domain.usecase

import com.muhmmad.domain.repo.UserRepo
import okhttp3.MultipartBody

class UserUseCase(private val repo: UserRepo) {
    suspend fun getUserInfo(token: String) = repo.getUserInfo(token)
    suspend fun uploadUserAvatar(token: String, image: MultipartBody.Part) =
        repo.uploadUserAvatar(token, image)

    suspend fun updateUserName(token: String, name: String) = repo.updateUserName(token, name)
    suspend fun updateUserBio(token: String, bio: String) = repo.updateUserBio(token, bio)
    suspend fun updatePassword(
        token: String,
        oldPassword: String,
        newPassword: String
    ) = repo.updatePassword(token, oldPassword, newPassword)

    suspend fun getAuthorInfo(userId: String) = repo.getAuthorInfo(userId)
}