package com.muhmmad.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val name: String,
    val avatar: Cover? = null,
)
