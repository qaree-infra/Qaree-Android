package com.muhmmad.qaree.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhmmad.domain.model.BaseResponse
import com.muhmmad.domain.usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewPasswordViewModel @Inject constructor(private val useCase: AuthUseCase) : ViewModel() {
    private val _state = MutableStateFlow(NewPasswordState())
    val state = _state.asStateFlow()
    fun newPassword(pass: String, token: String) = viewModelScope.launch(Dispatchers.IO) {
        _state.update {
            it.copy(
                isLoading = true,
                error = null,
                newPasswordResponse = null
            )
        }

        useCase.resetPassword(pass, token).apply {
            _state.update {
                it.copy(
                    newPasswordResponse = data,
                    isLoading = false,
                    error = message
                )
            }
        }
    }

    data class NewPasswordState(
        val newPasswordResponse: BaseResponse? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )
}