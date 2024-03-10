package com.muhmmad.domain.model

data class LibraryResponse(
    val data: List<Shelf>, val total: Int,
    val currentPage: Int,
    val numberOfPages: Int
)
