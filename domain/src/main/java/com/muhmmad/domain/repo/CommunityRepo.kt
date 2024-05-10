package com.muhmmad.domain.repo

import com.muhmmad.domain.model.BaseResponse
import com.muhmmad.domain.model.CommunityMembers
import com.muhmmad.domain.model.NetworkResponse
import io.socket.client.Socket

interface CommunityRepo {
    suspend fun joinCommunity(token: String, bookId: String): NetworkResponse<BaseResponse>
    suspend fun connectSocket(token: String)
    suspend fun disconnectSocket()
    suspend fun getSocket(): Socket
    suspend fun getCommunityMembers(
        id: String,
        page: Int,
        membersPerPage: Int,
        token: String
    ): NetworkResponse<CommunityMembers>
}