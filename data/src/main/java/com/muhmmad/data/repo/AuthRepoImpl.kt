package com.muhmmad.data.repo

import com.muhmmad.data.remote.AuthClient
import com.muhmmad.domain.repo.AuthRepo
import com.muhmmad.qaree.type.SigninType

class AuthRepoImpl(private val client: AuthClient):AuthRepo {
    override fun isUserLogged(): Boolean {
        TODO("Not yet implemented")
    }

    override fun login(email: String, pass: String): SigninType {
        TODO("Not yet implemented")
    }
}