package com.muhmmad.domain.model

data class Review(
    val id: String,
    val user: User,
    val rate: Float=0.0F,
    val content: String,
    val createdAt: String,
    val updatedAt: String
)
