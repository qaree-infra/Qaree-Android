package com.muhmmad.data.repo

import com.muhmmad.domain.model.BaseResponse
import com.muhmmad.domain.model.BookStatus
import com.muhmmad.domain.model.BooksResponse
import com.muhmmad.domain.model.NetworkResponse
import com.muhmmad.domain.model.PaymentOrder
import com.muhmmad.domain.model.ReviewsResponse
import com.muhmmad.domain.remote.GraphQlDataSource
import com.muhmmad.domain.repo.BookRepo

class BookRepoImpl(
    private val graphQlDataSource: GraphQlDataSource,
) : BookRepo {
    override suspend fun getBooksByCategory(categoryId: String): NetworkResponse<BooksResponse> =
        graphQlDataSource.getBooksByCategory(categoryId)

    override suspend fun search(name: String): NetworkResponse<BooksResponse> =
        graphQlDataSource.search(name)

    override suspend fun getBookReviews(id: String): NetworkResponse<ReviewsResponse> =
        graphQlDataSource.getBookReviews(id)

    override suspend fun makeReview(
        token: String,
        bookId: String,
        rate: Float,
        content: String
    ): NetworkResponse<BaseResponse> = graphQlDataSource.makeReview(token, bookId, rate, content)

    override suspend fun getBookStatus(token: String, bookId: String): NetworkResponse<BookStatus> =
        graphQlDataSource.getBookStatus(token, bookId)

    override suspend fun createPaymentOrder(
        token: String,
        bookId: String
    ): NetworkResponse<PaymentOrder> = graphQlDataSource.createPaymentOrder(token, bookId)
}