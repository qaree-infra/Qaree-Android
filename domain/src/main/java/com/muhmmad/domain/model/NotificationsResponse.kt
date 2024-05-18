package com.muhmmad.domain.model

data class NotificationsResponse(
    val notifications: List<Notification>,
    val currentPage: Int = 0,
    val numberOfPages: Int = 0
)
