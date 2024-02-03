package com.muhmmad.data

import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Operation
import com.muhmmad.domain.model.LoginResponse
import com.muhmmad.domain.model.NetworkResponse
import com.muhmmad.qaree.SignInMutation

fun SignInMutation.Signin.toLoginResponse(): LoginResponse = LoginResponse(
    message = message ?: "",
    token = access_token ?: "",
    error = ""
)

fun <T : Operation.Data> checkResponse(response: ApolloResponse<T>): NetworkResponse<T> {
    return if (response.hasErrors()) {
        NetworkResponse.Error(
            response.errors?.get(0)?.message.toString(),
        )
    } else {
        NetworkResponse.Success(response.data!!)
    }
}

