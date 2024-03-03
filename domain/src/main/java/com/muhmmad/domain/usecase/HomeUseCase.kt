package com.muhmmad.domain.usecase

import com.muhmmad.domain.repo.HomeRepo

class HomeUseCase(private val repo: HomeRepo) {
    suspend fun getOffers() = repo.getOffers()
}