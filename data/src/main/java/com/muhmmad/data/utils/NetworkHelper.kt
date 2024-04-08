package com.muhmmad.data.utils

import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Operation
import com.muhmmad.domain.model.NetworkResponse
import retrofit2.Response

fun <T> Response<T>.checkResponse(): NetworkResponse<T> {
    return if (isSuccessful) NetworkResponse.Success(body()!!) else NetworkResponse.Error(
        message = "Error",
        data = body()
    )
}

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