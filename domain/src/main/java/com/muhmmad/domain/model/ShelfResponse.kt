package com.muhmmad.domain.model

data class ShelfResponse(
    val id: String,
    val name: String,
    val books: List<Book>,
    val total: Int,
    val currentPage: Int,
    val numberOfPages: Int
)
