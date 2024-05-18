package com.muhmmad.domain.model

data class Notification(
    val id: String,
    val title: String,
    val body: String,
    val image: String,
    val type: String,
    val data: NotificationData?,
    val createdAt: String,
    val updatedAt: String
)
