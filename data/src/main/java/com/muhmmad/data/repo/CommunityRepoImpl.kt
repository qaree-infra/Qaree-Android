package com.muhmmad.data.repo

import com.muhmmad.data.remote.SocketHandler
import com.muhmmad.domain.model.BaseResponse
import com.muhmmad.domain.model.CommunityMembers
import com.muhmmad.domain.model.NetworkResponse
import com.muhmmad.domain.remote.GraphQlDataSource
import com.muhmmad.domain.repo.CommunityRepo

class CommunityRepoImpl(private val graphQlDataSource: GraphQlDataSource) : CommunityRepo {
    override suspend fun joinCommunity(
        token: String,
        bookId: String
    ): NetworkResponse<BaseResponse> = graphQlDataSource.joinCommunity(token, bookId)

    override suspend fun connectSocket(token: String) = SocketHandler.connectSocket(token)

    override suspend fun disconnectSocket() = SocketHandler.closeConnection()

    override suspend fun getSocket() = SocketHandler.getSocket()
    override suspend fun getCommunityMembers(
        id: String,
        page: Int,
        membersPerPage: Int,
        token: String
    ): NetworkResponse<CommunityMembers> =
        graphQlDataSource.getCommunityMembers(id, page, membersPerPage, token)
    // override suspend fun getRooms(token: String) = SocketHandler.getRooms(token)
}