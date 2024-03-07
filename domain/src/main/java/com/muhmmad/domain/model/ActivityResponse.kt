package com.muhmmad.domain.model

data class ActivityResponse(
    val book: Book,
    val createdAt: String,
    val readingProgress: Int,
    val status: String,
    val updatedAt: String
)