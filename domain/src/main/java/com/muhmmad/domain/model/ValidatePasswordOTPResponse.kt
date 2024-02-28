package com.muhmmad.domain.model

data class ValidatePasswordOTPResponse(
    val message: String,
    val success: Boolean,
    val reset_token: String
)