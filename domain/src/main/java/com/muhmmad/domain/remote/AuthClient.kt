package com.muhmmad.domain.remote

import com.muhmmad.domain.model.LoginResponse

interface AuthClient {
    suspend fun login(email: String, pass: String): LoginResponse
}