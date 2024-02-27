package com.muhmmad.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    val name: String = "",
    val email: String = "",
    val token: String = "",
    val language: Language = Language.ENGLISH,
    val darkMode: Boolean = false,
)

enum class Language {
    ENGLISH, ARABIC
}
