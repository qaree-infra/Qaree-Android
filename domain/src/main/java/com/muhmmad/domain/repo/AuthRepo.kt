package com.muhmmad.domain.repo

import com.muhmmad.domain.model.LoginResponse
import com.muhmmad.domain.model.NetworkResponse
import com.muhmmad.domain.model.VerificationResponse

interface AuthRepo {
    fun isUserLogged(): Boolean
    suspend fun login(email: String, pass: String): NetworkResponse<LoginResponse>
    suspend fun register(name: String, email: String, pass: String): NetworkResponse<String>
    suspend fun verifyAccount(email: String, otp: String): NetworkResponse<VerificationResponse>
}