package com.muhmmad.domain.repo

import com.muhmmad.domain.model.LoginResponse
import com.muhmmad.domain.model.NetworkResponse
import com.muhmmad.domain.model.ValidatePasswordOTPResponse
import com.muhmmad.domain.model.BaseResponse

interface AuthRepo {
    suspend fun login(email: String, pass: String): NetworkResponse<LoginResponse>
    suspend fun register(name: String, email: String, pass: String): NetworkResponse<String>
    suspend fun verifyAccount(email: String, otp: String): NetworkResponse<BaseResponse>
    suspend fun resendVerifyOTP(email: String): NetworkResponse<BaseResponse>
    suspend fun forgotPassword(email: String): NetworkResponse<BaseResponse>
    suspend fun validatePasswordOTP(
        email: String,
        otp: String
    ): NetworkResponse<ValidatePasswordOTPResponse>

    suspend fun resendPasswordOTP(email: String): NetworkResponse<BaseResponse>
    suspend fun resetPassword(pass: String, token: String): NetworkResponse<BaseResponse>
    suspend fun getToken(): String
    suspend fun setToken(token: String)
}