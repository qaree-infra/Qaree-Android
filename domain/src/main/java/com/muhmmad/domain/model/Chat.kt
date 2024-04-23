package com.muhmmad.domain.model

import kotlinx.serialization.SerialName
import java.io.Serializable

data class Chat(
    @SerialName("_id")
    val _id: String,
    @SerialName("lastMessage")
    val lastMessage: Message,
    val activation: Boolean,
    val creator: String,
    val partner: User,
    val roomId: String,
    val book: Book,
) : Serializable {
    fun getImage(): String = book.cover.path.ifEmpty { partner.avatar?.path ?: "" }

    fun getName(): String = book.name.ifEmpty { partner.name ?: "" }
}