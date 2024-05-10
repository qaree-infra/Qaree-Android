package com.muhmmad.domain.model

data class CommunityMembers(
    val members: List<User> = emptyList(),
    val currentPage: Int = 0,
    val numberOfPages: Int = 0,
    val totalMembers: Int = 0
)
