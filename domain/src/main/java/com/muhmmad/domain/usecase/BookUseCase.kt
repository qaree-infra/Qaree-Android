package com.muhmmad.domain.usecase

import com.muhmmad.domain.repo.BookRepo

class BookUseCase(private val repo: BookRepo) {
    suspend fun getBookReviews(id: String) = repo.getBookReviews(id)
    suspend fun makeReview(token: String, bookId: String, rate: Float, content: String) =
        repo.makeReview(token, bookId, rate, content)

    suspend fun getBooksByCategory(categoryId: String) = repo.getBooksByCategory(categoryId)
    suspend fun search(name: String) = repo.search(name)
    suspend fun getBookStatus(token: String, bookId: String) = repo.getBookStatus(token, bookId)
    suspend fun createPaymentOrder(token: String, bookId: String) =
        repo.createPaymentOrder(token, bookId)
}