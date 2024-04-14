package com.muhmmad.domain.usecase

import com.muhmmad.domain.repo.CommunityRepo

class CommunityUseCase(private val repo: CommunityRepo) {
    suspend fun joinCommunity(token: String, bookId: String) = repo.joinCommunity(token, bookId)
    suspend fun connectSocket() = repo.connectSocket()
    suspend fun disconnectSocket() = repo.disconnectSocket()
}