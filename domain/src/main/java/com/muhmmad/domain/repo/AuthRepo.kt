package com.muhmmad.domain.repo

import com.muhmmad.domain.model.LoginResponse
import com.muhmmad.domain.model.NetworkResponse

interface AuthRepo {
    fun isUserLogged(): Boolean
    suspend fun login(email: String, pass: String): NetworkResponse<LoginResponse>
}