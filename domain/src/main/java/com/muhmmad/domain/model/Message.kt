package com.muhmmad.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Message(
    val _id: String,
    val content: String,
    val sender: User,
    val reader: List<String?>,
    val room: String,
    val createdAt: String,
    val updatedAt: String
) : Parcelable