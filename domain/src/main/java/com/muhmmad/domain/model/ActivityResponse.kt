package com.muhmmad.domain.model

data class ActivityResponse(
    val book: Book,
    val createdAt: String,
    val readingProgress: Double,
    val status: String,
    val updatedAt: String
)