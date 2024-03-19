package com.muhmmad.domain.model

data class Offer(
    val percent: Int = 0,
    val expireAt: String = "",
    val book: Book
)
