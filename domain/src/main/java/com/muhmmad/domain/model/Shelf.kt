package com.muhmmad.domain.model

data class Shelf(
    val id: String,
    val name: String,
    val books: List<Book>
)
