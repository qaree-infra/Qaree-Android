package com.muhmmad.domain.usecase

import com.muhmmad.domain.repo.AuthRepo

class AuthUseCase(private val repo: AuthRepo) {
    suspend fun login(email: String, pass: String) = repo.login(email, pass)
    suspend fun register(name: String, email: String, pass: String) =
        repo.register(name, email, pass)

    suspend fun verifyAccount(email: String, otp: String) = repo.verifyAccount(email, otp)
    suspend fun resendVerifyOTP(email: String) = repo.resendVerifyOTP(email)
    suspend fun forgotPassword(email: String) = repo.forgotPassword(email)
    suspend fun getToken() = repo.getToken()
}