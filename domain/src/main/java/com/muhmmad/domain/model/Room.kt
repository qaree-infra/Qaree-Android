package com.muhmmad.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Room(
    val _id: String = "",
    var lastMessage: Message = Message(),
    val activation: Boolean = false,
    val creator: String = "",
    val partner: User = User(),
    val roomId: String = "",
    val book: Book = Book(),
) : Parcelable {
    fun getImage(): String = book.cover.path.ifEmpty { partner.avatar?.path ?: "" }

    fun getName(): String = book.name.ifEmpty { partner.name }
}