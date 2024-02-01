package com.muhmmad.data.remote

import com.apollographql.apollo3.ApolloClient
import com.muhmmad.data.toLoginResponse
import com.muhmmad.domain.model.LoginResponse
import com.muhmmad.domain.remote.AuthClient
import com.muhmmad.qaree.SignInMutation

class ApolloAuthClient(
    private val apolloClient: ApolloClient
) : AuthClient {
    override suspend fun login(email: String, pass: String): LoginResponse {
        return try {
            apolloClient.mutation(SignInMutation(email, pass))
                .execute().data?.signin?.toLoginResponse()!!
        } catch (exception: Exception) {
            LoginResponse("", "", "")
        }
    }
}