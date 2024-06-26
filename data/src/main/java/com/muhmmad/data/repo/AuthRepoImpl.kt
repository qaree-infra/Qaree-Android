package com.muhmmad.data.repo

import com.muhmmad.domain.local.LocalDataSource
import com.muhmmad.domain.model.LoginResponse
import com.muhmmad.domain.model.NetworkResponse
import com.muhmmad.domain.model.ValidatePasswordOTPResponse
import com.muhmmad.domain.model.BaseResponse
import com.muhmmad.domain.remote.GraphQlDataSource
import com.muhmmad.domain.repo.AuthRepo

class AuthRepoImpl(
    private val graphQlDataSource: GraphQlDataSource,
    private val localDataSource: LocalDataSource
) : AuthRepo {
    override suspend fun setFirstTime(isFirstTime: Boolean) =
        localDataSource.setFirstTime(isFirstTime)

    override suspend fun isFirstTime(): Boolean = localDataSource.isFirstTime()

    override suspend fun login(
        email: String,
        pass: String,
        token: String
    ): NetworkResponse<LoginResponse> = graphQlDataSource.login(email, pass, token)

    override suspend fun loginWithGoogle(
        socialToken: String,
        firebaseToken: String
    ): NetworkResponse<LoginResponse> =
        graphQlDataSource.loginWithGoogle(socialToken, firebaseToken)

    override suspend fun register(
        name: String,
        email: String,
        pass: String
    ): NetworkResponse<String> = graphQlDataSource.register(name, email, pass)

    override suspend fun verifyAccount(
        email: String,
        otp: String
    ): NetworkResponse<BaseResponse> = graphQlDataSource.verifyAccount(email, otp)

    override suspend fun resendVerifyOTP(email: String): NetworkResponse<BaseResponse> =
        graphQlDataSource.resendVerifyOTP(email)

    override suspend fun forgotPassword(email: String): NetworkResponse<BaseResponse> =
        graphQlDataSource.forgotPassword(email)

    override suspend fun validatePasswordOTP(
        email: String,
        otp: String
    ): NetworkResponse<ValidatePasswordOTPResponse> =
        graphQlDataSource.validatePasswordOTP(email, otp)

    override suspend fun resendPasswordOTP(email: String): NetworkResponse<BaseResponse> =
        graphQlDataSource.resendPasswordOTP(email)

    override suspend fun resetPassword(pass: String, token: String): NetworkResponse<BaseResponse> =
        graphQlDataSource.resetPassword(pass, token)

    override suspend fun getToken(): String = localDataSource.getToken()
    override suspend fun setToken(token: String) = localDataSource.setToken(token)

    override suspend fun logout() = localDataSource.logout()
    override suspend fun deleteAccount(token: String): NetworkResponse<BaseResponse> =
        graphQlDataSource.deleteAccount(token)

    override suspend fun deleteUserData() = localDataSource.deleteUserData()
}