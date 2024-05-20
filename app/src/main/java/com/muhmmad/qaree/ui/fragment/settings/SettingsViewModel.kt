package com.muhmmad.qaree.ui.fragment.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhmmad.domain.model.AppMode
import com.muhmmad.domain.usecase.AuthUseCase
import com.muhmmad.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val userUseCase: UserUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()

    fun logout() = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
        authUseCase.logout().apply {
            _state.update {
                it.copy(
                    isLoading = false,
                    isLogout = true
                )
            }
        }
    }

    fun changeUiMode(mode: AppMode) = viewModelScope.launch {
        userUseCase.changeMode(mode)
    }

    data class SettingsState(
        val isLoading: Boolean = false,
        val error: String? = null,
        val message: String? = null,
        val isLogout: Boolean = false
    )
}