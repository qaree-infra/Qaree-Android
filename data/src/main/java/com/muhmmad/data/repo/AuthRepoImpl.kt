package com.muhmmad.data.repo

import com.muhmmad.domain.model.LoginResponse
import com.muhmmad.domain.model.NetworkResponse
import com.muhmmad.domain.model.VerificationResponse
import com.muhmmad.domain.remote.AuthClient
import com.muhmmad.domain.repo.AuthRepo

class AuthRepoImpl(private val client: AuthClient) : AuthRepo {
    override fun isUserLogged(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun login(email: String, pass: String): NetworkResponse<LoginResponse> =
        client.login(email, pass)

    override suspend fun register(
        name: String,
        email: String,
        pass: String
    ): NetworkResponse<String> = client.register(name, email, pass)

    override suspend fun verifyAccount(
        email: String,
        otp: String
    ): NetworkResponse<VerificationResponse> = client.verifyAccount(email, otp)

    override suspend fun resendVerifyOTP(email: String): NetworkResponse<VerificationResponse> =
        client.resendVerifyOTP(email)
}