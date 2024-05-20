package com.muhmmad.domain.model

data class Messages(
    val messages: ArrayList<Message>,
    val totalMessages: Int,
    val currentPage: Int,
    val numberOfPages: Int
)