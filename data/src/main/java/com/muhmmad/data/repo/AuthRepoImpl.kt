package com.muhmmad.data.repo

import com.muhmmad.domain.model.LoginResponse
import com.muhmmad.domain.remote.AuthClient
import com.muhmmad.domain.repo.AuthRepo

class AuthRepoImpl(private val client: AuthClient) : AuthRepo {
    override fun isUserLogged(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun login(email: String, pass: String): LoginResponse = client.login(email, pass)
}