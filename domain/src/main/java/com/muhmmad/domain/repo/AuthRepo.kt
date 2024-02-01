package com.muhmmad.domain.repo

import com.muhmmad.domain.model.LoginResponse

interface AuthRepo {
    fun isUserLogged(): Boolean
    suspend fun login(email: String, pass: String): LoginResponse
}