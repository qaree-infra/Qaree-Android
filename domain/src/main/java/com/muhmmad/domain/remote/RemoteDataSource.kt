package com.muhmmad.domain.remote

import com.muhmmad.domain.model.LoginResponse
import com.muhmmad.domain.model.NetworkResponse
import com.muhmmad.domain.model.VerificationResponse

interface RemoteDataSource {
    suspend fun login(email: String, pass: String): NetworkResponse<LoginResponse>
    suspend fun register(name: String, email: String, pass: String): NetworkResponse<String>
    suspend fun verifyAccount(email: String, otp: String): NetworkResponse<VerificationResponse>
    suspend fun resendVerifyOTP(email: String): NetworkResponse<VerificationResponse>
    suspend fun forgotPassword(email: String): NetworkResponse<VerificationResponse>
}