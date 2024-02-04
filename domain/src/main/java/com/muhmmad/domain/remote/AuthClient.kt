package com.muhmmad.domain.remote

import com.muhmmad.domain.model.LoginResponse
import com.muhmmad.domain.model.NetworkResponse
import com.muhmmad.domain.model.VerificationResponse

interface AuthClient {
    suspend fun login(email: String, pass: String): NetworkResponse<LoginResponse>
    suspend fun register(name: String, email: String, pass: String): NetworkResponse<String>
    suspend fun verifyAccount(email: String, otp: String): NetworkResponse<VerificationResponse>
}