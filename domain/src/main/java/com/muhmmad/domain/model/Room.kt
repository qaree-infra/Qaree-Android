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
    fun getImage(): String =
        try {
            book.cover.path.ifEmpty { partner.avatar?.path ?: "" }
        } catch (ex: Exception) {
            ""
        }


    fun getName(): String = book.name.ifEmpty { partner.name }
}