package com.muhmmad.domain.repo

import com.muhmmad.domain.model.BaseResponse
import com.muhmmad.domain.model.BookStatus
import com.muhmmad.domain.model.BooksResponse
import com.muhmmad.domain.model.NetworkResponse
import com.muhmmad.domain.model.ReviewsResponse

interface BookRepo {
    suspend fun getBooksByCategory(categoryId: String): NetworkResponse<BooksResponse>
    suspend fun search(name: String): NetworkResponse<BooksResponse>
    suspend fun getBookReviews(id: String): NetworkResponse<ReviewsResponse>
    suspend fun makeReview(
        token: String,
        bookId: String,
        rate: Float,
        content: String
    ): NetworkResponse<BaseResponse>

    suspend fun getBookStatus(token: String, bookId: String): NetworkResponse<BookStatus>
}