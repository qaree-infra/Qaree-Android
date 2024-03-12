package com.muhmmad.data

import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Operation
import com.apollographql.apollo3.network.http.HttpInfo
import com.muhmmad.domain.model.NetworkResponse

object NetworkOperations {
    fun <T : Operation.Data> checkResponse(response: ApolloResponse<T>): NetworkResponse<T> {
        return if (response.hasErrors()) {
            //response.executionContext[HttpInfo].statusCode
            NetworkResponse.Error(
                response.errors?.get(0)?.message.toString(),
            )
        } else {
            NetworkResponse.Success(response.data!!)
        }
    }
}