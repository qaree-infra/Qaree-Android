package com.muhmmad.data.remote

import com.apollographql.apollo3.ApolloClient
import com.muhmmad.data.checkResponse
import com.muhmmad.data.toLoginResponse
import com.muhmmad.domain.model.LoginResponse
import com.muhmmad.domain.model.NetworkResponse
import com.muhmmad.domain.remote.AuthClient
import com.muhmmad.qaree.SignInMutation

class ApolloAuthClient(
    private val apolloClient: ApolloClient
) : AuthClient {
    override suspend fun login(email: String, pass: String): NetworkResponse<LoginResponse> {
        try {
            val response = checkResponse(
                apolloClient.mutation(SignInMutation(email, pass))
                    .execute()
            )

            return when (response) {
                is NetworkResponse.Success -> {
                    NetworkResponse.Success(
                        response.data?.signin?.toLoginResponse()!!
                    )
                }

                else -> {
                    NetworkResponse.Error(
                        response.message!!
                    )
                }
            }
        } catch (exception: Exception) {
            return NetworkResponse.Error(
                exception.localizedMessage?.toString()!!
            )
        }
    }
}