package com.muhmmad.domain.repo

import com.muhmmad.qaree.type.SigninType

interface AuthRepo {
    fun isUserLogged(): Boolean
    fun login(email: String, pass: String): SigninType
}