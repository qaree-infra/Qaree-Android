package com.muhmmad.domain.model

data class BooksResponse(
    val data: List<Book>, val total: Int = 0
)