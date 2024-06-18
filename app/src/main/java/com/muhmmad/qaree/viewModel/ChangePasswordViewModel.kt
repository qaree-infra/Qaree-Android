package com.muhmmad.qaree.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhmmad.domain.model.User
import com.muhmmad.domain.usecase.AuthUseCase
import com.muhmmad.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val useCase: UserUseCase,
    private val authUseCase: AuthUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ChangePasswordState())
    val state = _state.asStateFlow()

    fun changePassword(oldPassword: String, newPassword: String) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        useCase.updatePassword(authUseCase.getToken(), oldPassword, newPassword).apply {
            _state.update {
                it.copy(
                    isLoading = false,
                    error = message,
                    response = data
                )
            }
        }
    }

    data class ChangePasswordState(
        val response: User? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )
}