package com.muhmmad.qaree.ui.activity.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhmmad.domain.usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()
    fun isLogged() {
        viewModelScope.launch {
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
    }

    data class MainState(
        val isLogged: Boolean? = null,
        val error: String? = null
    )
}