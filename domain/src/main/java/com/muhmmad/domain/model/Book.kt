package com.muhmmad.domain.model

data class Book(
    val price: Double = 0.0,
    val name: String = "",
    val author: User? = null,
    val cover: Cover = Cover(),
    val categories: List<Category>? = null,
    val id: String = "",
    val isbn: String = "",
    val avgRating: Int = 0,
    val status: String = "",
    val readingProgress: Int = 0,
    val language: String = "",
    val description: String = "",
    val createdAt: String = ""
) : java.io.Serializable
