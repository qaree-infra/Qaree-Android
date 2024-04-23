package com.muhmmad.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val _id: String,
    val name: String,
    val avatar: Cover? = null,
    val email: String = "",
    val bio: String = ""
) : java.io.Serializable
