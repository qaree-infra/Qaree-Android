package com.muhmmad.domain.model

data class LoginResponse(
    val message: String,
    val token: String,
    val error: String
)
