package com.muhmmad.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Cover(
    val id: String = "",
    val path: String = "",
    val size: Double = 0.0
):java.io.Serializable
