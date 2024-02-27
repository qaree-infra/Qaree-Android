package com.muhmmad.qaree.ui.fragment.forget_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhmmad.domain.model.VerificationResponse
import com.muhmmad.domain.usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(private val useCase: AuthUseCase) : ViewModel() {
    private val _state = MutableStateFlow(ForgotPasswordState())
    val state = _state.asStateFlow()

    fun forgotPassword(email:String){
        viewModelScope.launch {
            _state.update {
                it.copy(
                    forgotPasswordResponse = null,
                    isLoading = true,
                    error = null
                )
            }

            useCase.forgotPassword(email).apply {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = message,
                        forgotPasswordResponse = data
                    )
                }
            }
        }
    }

    data class ForgotPasswordState(
        val forgotPasswordResponse: VerificationResponse? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )
}