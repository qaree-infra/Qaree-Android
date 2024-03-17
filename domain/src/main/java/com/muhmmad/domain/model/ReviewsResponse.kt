package com.muhmmad.domain.model

data class ReviewsResponse(
    val data: List<Review>,
    val total: Int = 0,
    val currentPage: Int = 0,
    val numberOfPages: Int = 0
)
