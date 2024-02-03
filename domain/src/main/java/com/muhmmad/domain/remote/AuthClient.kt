package com.muhmmad.domain.remote

import com.muhmmad.domain.model.LoginResponse
import com.muhmmad.domain.model.NetworkResponse

interface AuthClient {
    suspend fun login(email: String, pass: String): NetworkResponse<LoginResponse>
}