package com.muhmmad.domain.remote

import com.muhmmad.domain.model.ActivityResponse
import com.muhmmad.domain.model.AuthorsResponse
import com.muhmmad.domain.model.LoginResponse
import com.muhmmad.domain.model.NetworkResponse
import com.muhmmad.domain.model.ValidatePasswordOTPResponse
import com.muhmmad.domain.model.BaseResponse
import com.muhmmad.domain.model.BooksResponse
import com.muhmmad.domain.model.CategoriesResponse
import com.muhmmad.domain.model.OffersResponse

interface RemoteDataSource {
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
    suspend fun getOffers(): NetworkResponse<OffersResponse>

    suspend fun getLastActivity(token: String): NetworkResponse<ActivityResponse>
    suspend fun getTopAuthors(): NetworkResponse<AuthorsResponse>
    suspend fun getNewReleaseBooks(): NetworkResponse<BooksResponse>
    suspend fun getBestSellerBooks(): NetworkResponse<BooksResponse>
    suspend fun getCategories():NetworkResponse<CategoriesResponse>
}