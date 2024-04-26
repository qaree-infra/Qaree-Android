package com.muhmmad.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Chat(
    val _id: String?,
    val lastMessage: Message?,
    val activation: Boolean?,
    val creator: String?,
    val partner: User?,
    val roomId: String?,
    val book: Book?,
) : Parcelable {
    fun getImage(): String = book?.cover?.path?.ifEmpty { partner?.avatar?.path ?: "" } ?: ""

    fun getName(): String = book?.name?.ifEmpty { partner?.name ?: "" } ?: ""
}