package com.muhmmad.domain.usecase

import com.muhmmad.domain.repo.HomeRepo

class HomeUseCase(private val repo: HomeRepo) {
    suspend fun getOffers() = repo.getOffers()
    suspend fun getLastActivity(token: String) = repo.getLastActivity(token)
    suspend fun getTopAuthors() = repo.getTopAuthors()
    suspend fun getNewReleaseBooks() = repo.getNewReleaseBooks()
    suspend fun getBestSellerBooks() = repo.getBestSellerBooks()
    suspend fun getCategories() = repo.getCategories()
}