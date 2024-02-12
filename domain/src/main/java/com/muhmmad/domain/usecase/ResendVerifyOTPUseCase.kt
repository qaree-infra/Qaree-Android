package com.muhmmad.domain.usecase

import com.muhmmad.domain.repo.AuthRepo

class ResendVerifyOTPUseCase(private val repo: AuthRepo) {
    suspend operator fun invoke(email: String) = repo.resendVerifyOTP(email)
}