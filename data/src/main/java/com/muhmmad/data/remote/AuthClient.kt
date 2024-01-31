package com.muhmmad.data.remote

import com.muhmmad.qaree.type.SigninType

interface AuthClient {
    suspend fun login(email: String, pass: String): SigninType
}