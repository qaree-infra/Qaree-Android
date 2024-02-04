package com.muhmmad.domain.usecase

import com.muhmmad.domain.repo.AuthRepo

class RegisterUseCase(private val repo: AuthRepo) {
    suspend operator fun invoke(name: String, email: String, pass: String) =
        repo.register(name, email, pass)
}