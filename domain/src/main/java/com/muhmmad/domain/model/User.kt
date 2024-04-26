package com.muhmmad.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val _id: String,
    val name: String,
    val avatar: Cover? = null,
    val email: String = "",
    val bio: String = ""
) : Parcelable