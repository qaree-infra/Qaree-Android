package com.muhmmad.domain.usecase

import com.muhmmad.domain.repo.AuthRepo

class LoginUseCase(private val repo: AuthRepo) {
    suspend operator fun invoke(email: String, pass: String) = repo.login(email, pass)
}