package com.muhmmad.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: String,
    val nameAr: String,
    val nameEn: String,
    val image: String,
):java.io.Serializable