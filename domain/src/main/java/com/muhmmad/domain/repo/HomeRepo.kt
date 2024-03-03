package com.muhmmad.domain.repo

import com.muhmmad.domain.model.LoginResponse
import com.muhmmad.domain.model.NetworkResponse
import com.muhmmad.domain.model.OffersResponse

interface HomeRepo {
    suspend fun getOffers(): NetworkResponse<OffersResponse>
}