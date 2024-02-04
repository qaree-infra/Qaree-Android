package com.muhmmad.qaree.ui.fragment.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhmmad.domain.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val useCase: RegisterUseCase) : ViewModel() {
    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    fun register(name: String, email: String, pass: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    registerResponse = null,
                    isLoading = true,
                    error = null
                )
            }

            useCase(name, email, pass).apply {
                _state.update {
                    it.copy(
                        registerResponse = data,
                        isLoading = false,
                        error = message
                    )
                }
            }
        }
    }

    fun destroy() {
        _state.update {
            it.copy(
                registerResponse = null,
                isLoading = false,
                error = null
            )
        }
    }

    data class RegisterState(
        val registerResponse: String? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )
}