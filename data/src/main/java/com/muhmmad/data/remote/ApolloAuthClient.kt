package com.muhmmad.data.remote

import com.apollographql.apollo3.ApolloClient
import com.muhmmad.data.NetworkOperations.checkResponse
import com.muhmmad.data.toLoginResponse
import com.muhmmad.data.toVerificationResponse
import com.muhmmad.domain.model.LoginResponse
import com.muhmmad.domain.model.NetworkResponse
import com.muhmmad.domain.model.NetworkResponse.Error
import com.muhmmad.domain.model.NetworkResponse.Success
import com.muhmmad.domain.model.VerificationResponse
import com.muhmmad.domain.remote.AuthClient
import com.muhmmad.qaree.SignInMutation
import com.muhmmad.qaree.SignUpMutation
import com.muhmmad.qaree.VerifyAccountMutation

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
                is Success -> {
                    Success(
                        response.data?.signin?.toLoginResponse()!!
                    )
                }

                else -> {
                    Error(
                        response.message!!
                    )
                }
            }
        } catch (exception: Exception) {
            return Error(
                exception.localizedMessage?.toString()!!
            )
        }
    }

    override suspend fun register(
        name: String,
        email: String,
        pass: String
    ): NetworkResponse<String> {
        try {
            val response =
                checkResponse(apolloClient.mutation(SignUpMutation(name, email, pass)).execute())


            return when (response) {
                is Success -> {
                    Success(response.data?.signup?.message.toString())
                }

                else -> {
                    Error(
                        response.message.toString()
                    )
                }
            }

        } catch (ex: Exception) {
            return Error(
                ex.localizedMessage?.toString()!!
            )
        }
    }

    override suspend fun verifyAccount(
        email: String,
        otp: String
    ): NetworkResponse<VerificationResponse> {
        try {
            val response = checkResponse(
                apolloClient.mutation(VerifyAccountMutation(otp = otp, email = email)).execute()
            )

            return when (response) {
                is Success -> {
                    Success(response.data?.verifyAccount?.toVerificationResponse()!!)
                }

                else -> {
                    Error(
                        response.message.toString()
                    )
                }
            }

        } catch (ex: Exception) {
            return Error(ex.localizedMessage?.toString()!!)
        }
    }
}