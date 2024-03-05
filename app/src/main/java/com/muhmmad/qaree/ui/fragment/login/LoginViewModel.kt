package com.muhmmad.qaree.ui.fragment.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhmmad.domain.model.LoginResponse
import com.muhmmad.domain.usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "LoginViewModel"
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()
    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()
    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    fun updateEmail(newEmail: String) {
        _email.update { newEmail }
    }

    fun updatePassword(pass: String) {
        _password.update { pass }
    }

    fun login() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    loginResponse = null,
                    isLoading = true,
                    error = null
                )
            }

            authUseCase.login(_email.value, _password.value).apply {
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

    fun saveToken(token: String) {
        Log.i(TAG, token.toString())
        viewModelScope.launch {
            authUseCase.setToken(token)
        }
    }


    data class LoginState(
        val loginResponse: LoginResponse? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )
}