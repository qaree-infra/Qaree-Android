package com.muhmmad.data.repo

import com.muhmmad.data.remote.SocketHandler
import com.muhmmad.domain.model.BaseResponse
import com.muhmmad.domain.model.NetworkResponse
import com.muhmmad.domain.remote.GraphQlDataSource
import com.muhmmad.domain.repo.CommunityRepo

class CommunityRepoImpl(private val graphQlDataSource: GraphQlDataSource) : CommunityRepo {
    override suspend fun joinCommunity(
        token: String,
        bookId: String
    ): NetworkResponse<BaseResponse> = graphQlDataSource.joinCommunity(token, bookId)

    override suspend fun connectSocket() {
        SocketHandler.setSocket()
        SocketHandler.establishConnection()
    }

    override suspend fun disconnectSocket() = SocketHandler.closeConnection()
}