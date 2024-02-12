package com.muhmmad.domain.usecase

import com.muhmmad.domain.repo.AuthRepo

class VerificationUseCase(private val repo: AuthRepo) {
    suspend fun verifyAccount(email: String, otp: String) = repo.verifyAccount(email, otp)
    suspend fun resendVerifyOTP(email: String) = repo.resendVerifyOTP(email)
}