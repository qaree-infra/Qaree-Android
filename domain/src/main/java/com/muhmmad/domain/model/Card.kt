package com.muhmmad.domain.model

data class Card(
    val id: Int,
    val name: String,
    val number: String,
    val cvc: Int,
    val expireDate: String,
    val image: Int
)
