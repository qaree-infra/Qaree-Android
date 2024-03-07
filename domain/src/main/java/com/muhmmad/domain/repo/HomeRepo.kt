package com.muhmmad.domain.repo

import com.muhmmad.domain.model.ActivityResponse
import com.muhmmad.domain.model.AuthorsResponse
import com.muhmmad.domain.model.LoginResponse
import com.muhmmad.domain.model.NetworkResponse
import com.muhmmad.domain.model.OffersResponse

interface HomeRepo {
    suspend fun getOffers(): NetworkResponse<OffersResponse>
    suspend fun getLastActivity(token: String): NetworkResponse<ActivityResponse>
    suspend fun getTopAuthors(): NetworkResponse<AuthorsResponse>
}