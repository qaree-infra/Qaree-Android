package com.muhmmad.domain.repo

import com.muhmmad.domain.model.BaseResponse
import com.muhmmad.domain.model.NetworkResponse

interface CommunityRepo {
    suspend fun joinCommunity(token: String, bookId: String): NetworkResponse<BaseResponse>
    suspend fun connectSocket()
    suspend fun disconnectSocket()
}