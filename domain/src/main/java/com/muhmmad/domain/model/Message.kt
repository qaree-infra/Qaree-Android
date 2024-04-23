package com.muhmmad.domain.model

import kotlinx.serialization.SerialName

data class Message(
    @SerialName("_id")
    val _id: String,
    val content: String,
    val sender: User,
    val reader: List<Any?>,
    @SerialName("room")
    val roomId: String,
    val createdAt: String,
    val updatedAt: String
)
