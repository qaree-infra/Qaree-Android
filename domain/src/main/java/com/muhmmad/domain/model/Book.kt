package com.muhmmad.domain.model

data class Book(
    val price: Double = 0.0,
    val name: String = "",
    val author: Author? = null,
    val cover: Cover,
    val categories: List<Category>? = null,
    val id: String,
    val isbn: String = "",
    val avgRating: Int = 0
)
