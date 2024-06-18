package com.muhmmad.qaree.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhmmad.domain.model.NotificationsResponse
import com.muhmmad.domain.usecase.AuthUseCase
import com.muhmmad.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val userUseCase: UserUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(NotificationsState())
    val state = _state.asStateFlow()
    fun getNotifications() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        userUseCase.getNotifications(authUseCase.getToken(), page = 1, limit = 20).apply {
            _state.update { it.copy(isLoading = false, error = message, notifications = data) }
        }
    }

    data class NotificationsState(
        val isLoading: Boolean = false,
        val error: String? = null,
        val notifications: NotificationsResponse? = null
    )
}