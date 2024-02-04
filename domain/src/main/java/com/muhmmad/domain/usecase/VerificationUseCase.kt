package com.muhmmad.domain.usecase

import com.muhmmad.domain.repo.AuthRepo

class VerificationUseCase(private val repo: AuthRepo) {
    suspend operator fun invoke(email: String, otp: String) = repo.verifyAccount(email, otp)
}