package com.muhmmad.qaree.ui.fragment.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import com.muhmmad.domain.model.LoginResponse
import com.muhmmad.domain.usecase.AuthUseCase
import com.muhmmad.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val userUseCase: UserUseCase
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

    fun login() = viewModelScope.launch(Dispatchers.IO) {
        _state.update {
            it.copy(
                loginResponse = null,
                isLoading = true,
                error = null
            )
        }

        authUseCase.login(
            _email.value,
            _password.value,
            FirebaseMessaging.getInstance().token.await()
        ).apply {
            _state.update {
                it.copy(
                    loginResponse = data,
                    isLoading = false,
                    error = message
                )
            }
        }
    }

    fun loginWithGoogle(socialToken: String) = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoading = true
            )
        }

        authUseCase.loginWithGoogle(socialToken, FirebaseMessaging.getInstance().token.await()).apply {
                Log.i(TAG, data.toString())
                Log.i(TAG, message.toString())
                _state.update {
                    it.copy(loginResponse = data, isLoading = false, error = message)
                }
            }
    }

    fun getUserData() = viewModelScope.launch(Dispatchers.IO) {
        userUseCase.getUserInfo(authUseCase.getToken()).apply {
            data?.let {
                userUseCase.saveUserData(it)
            }
        }
    }

    fun saveToken(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authUseCase.setToken(token).apply {
                _state.update {
                    it.copy(goHome = true)
                }
            }
        }
    }


    data class LoginState(
        val loginResponse: LoginResponse? = null,
        val isLoading: Boolean = false,
        val error: String? = null,
        val goHome: Boolean = false
    )
}

private const val TAG = "LoginViewModel"