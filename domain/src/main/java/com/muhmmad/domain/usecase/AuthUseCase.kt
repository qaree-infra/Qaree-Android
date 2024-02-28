package com.muhmmad.domain.usecase

import com.muhmmad.domain.repo.AuthRepo

class AuthUseCase(private val repo: AuthRepo) {
    suspend fun login(email: String, pass: String) = repo.login(email, pass)
    suspend fun register(name: String, email: String, pass: String) =
        repo.register(name, email, pass)

    suspend fun verifyAccount(email: String, otp: String) = repo.verifyAccount(email, otp)
    suspend fun resendVerifyOTP(email: String) = repo.resendVerifyOTP(email)
    suspend fun forgotPassword(email: String) = repo.forgotPassword(email)
    suspend fun validatePasswordOTP(email: String, otp: String) =
        repo.validatePasswordOTP(email, otp)

    suspend fun resendPasswordOTP(email: String) = repo.resendPasswordOTP(email)
    suspend fun resetPassword(pass: String, token: String) = repo.resetPassword(pass, token)
    suspend fun getToken() = repo.getToken()
    suspend fun setToken(token: String) = repo.setToken(token)
}