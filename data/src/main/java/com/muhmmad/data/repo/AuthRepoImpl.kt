package com.muhmmad.data.repo

import com.muhmmad.domain.model.LoginResponse
import com.muhmmad.domain.model.NetworkResponse
import com.muhmmad.domain.model.VerificationResponse
import com.muhmmad.domain.remote.RemoteDataSource
import com.muhmmad.domain.repo.AuthRepo

class AuthRepoImpl(private val dataSource: RemoteDataSource) : AuthRepo {
    override fun isUserLogged(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun login(email: String, pass: String): NetworkResponse<LoginResponse> =
        dataSource.login(email, pass)

    override suspend fun register(
        name: String,
        email: String,
        pass: String
    ): NetworkResponse<String> = dataSource.register(name, email, pass)

    override suspend fun verifyAccount(
        email: String,
        otp: String
    ): NetworkResponse<VerificationResponse> = dataSource.verifyAccount(email, otp)

    override suspend fun resendVerifyOTP(email: String): NetworkResponse<VerificationResponse> =
        dataSource.resendVerifyOTP(email)

    override suspend fun forgotPassword(email: String): NetworkResponse<VerificationResponse> =
        dataSource.forgotPassword(email)

    override suspend fun getToken(): String {
        TODO("Not yet implemented")
    }
}