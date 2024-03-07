package com.muhmmad.domain.model

data class Book(
    val price: Double,
    val name: String,
    val author: Author?,
    val cover: Cover,
    val categories: List<Category>?,
    val id: String,
    val isbn: String
)
