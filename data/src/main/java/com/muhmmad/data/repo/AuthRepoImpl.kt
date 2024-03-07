package com.muhmmad.data.repo

import com.muhmmad.domain.local.LocalDataSource
import com.muhmmad.domain.model.LoginResponse
import com.muhmmad.domain.model.NetworkResponse
import com.muhmmad.domain.model.ValidatePasswordOTPResponse
import com.muhmmad.domain.model.BaseResponse
import com.muhmmad.domain.remote.RemoteDataSource
import com.muhmmad.domain.repo.AuthRepo

class AuthRepoImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : AuthRepo {
    override suspend fun setFirstTime(isFirstTime: Boolean) =
        localDataSource.setFirstTime(isFirstTime)

    override suspend fun isFirstTime(): Boolean = localDataSource.isFirstTime()

    override suspend fun login(email: String, pass: String): NetworkResponse<LoginResponse> =
        remoteDataSource.login(email, pass)

    override suspend fun register(
        name: String,
        email: String,
        pass: String
    ): NetworkResponse<String> = remoteDataSource.register(name, email, pass)

    override suspend fun verifyAccount(
        email: String,
        otp: String
    ): NetworkResponse<BaseResponse> = remoteDataSource.verifyAccount(email, otp)

    override suspend fun resendVerifyOTP(email: String): NetworkResponse<BaseResponse> =
        remoteDataSource.resendVerifyOTP(email)

    override suspend fun forgotPassword(email: String): NetworkResponse<BaseResponse> =
        remoteDataSource.forgotPassword(email)

    override suspend fun validatePasswordOTP(
        email: String,
        otp: String
    ): NetworkResponse<ValidatePasswordOTPResponse> =
        remoteDataSource.validatePasswordOTP(email, otp)

    override suspend fun resendPasswordOTP(email: String): NetworkResponse<BaseResponse> =
        remoteDataSource.resendPasswordOTP(email)

    override suspend fun resetPassword(pass: String, token: String): NetworkResponse<BaseResponse> =
        remoteDataSource.resetPassword(pass, token)

    override suspend fun getToken(): String = localDataSource.getToken()
    override suspend fun setToken(token: String) {
        localDataSource.setToken(token)
    }
}