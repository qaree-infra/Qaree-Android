package com.muhmmad.qaree.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhmmad.domain.model.AppMode
import com.muhmmad.domain.usecase.AuthUseCase
import com.muhmmad.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val userUseCase: UserUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()
    fun isLogged() = viewModelScope.launch(Dispatchers.IO) {
        if (authUseCase.getToken().isNotEmpty()) {
            _state.update {
                it.copy(
                    isLogged = true
                )
            }
        } else {
            _state.update {
                it.copy(
                    isLogged = false
                )
            }
        }
    }

    fun setFirstTime() = viewModelScope.launch(Dispatchers.IO) {
        authUseCase.setFirstTime(false)
    }

    fun isFirstTime() = viewModelScope.launch(Dispatchers.IO) {
        _state.update { it.copy(isFirstTime = authUseCase.isFirstTime()) }
    }

    fun getUiMode() = viewModelScope.launch {
        userUseCase.getUiMode().apply {
            _state.update { it.copy(uiMode = this) }
        }
    }

    data class MainState(
        val isFirstTime: Boolean? = null,
        val isLogged: Boolean? = null,
        val error: String? = null,
        val uiMode: AppMode? = null
    )
}