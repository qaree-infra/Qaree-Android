package com.muhmmad.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Chat(
    val messages: List<Message> = emptyList(),
    val totalMessages: Int = 0,
    val currentPage: Int = 0,
    val numberOfPages: Int = 0
) : Parcelable
