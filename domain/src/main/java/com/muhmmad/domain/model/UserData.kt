package com.muhmmad.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val token: String = "",
    val isFirstTime: Boolean = true,
    val language: Language = Language.ENGLISH,
    val darkMode: Boolean = false,
)

enum class Language {
    ENGLISH, ARABIC
}
