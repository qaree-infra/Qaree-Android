package com.muhmmad.qaree.ui.fragment.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhmmad.domain.model.LoginResponse
import com.muhmmad.domain.model.NetworkResponse
import com.muhmmad.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    fun login(email: String, pass: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    loginResponse = null,
                    isLoading = true,
                    error = null
                )
            }

            loginUseCase(email, pass).apply {
                _state.update {
                    it.copy(
                        loginResponse = this.data,
                        isLoading = false,
                        error = this.message
                    )
                }
            }
        }
    }


    data class LoginState(
        val loginResponse: LoginResponse? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )
}