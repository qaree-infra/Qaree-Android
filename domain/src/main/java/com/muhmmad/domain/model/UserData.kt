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
    val darkMode: AppMode = AppMode.DEFAULT,
    val notifications: Boolean = true
)

enum class Language {
    ENGLISH, ARABIC
}

enum class AppMode {
    DEFAULT, LIGHT, DARK
}
