package com.muhmmad.qaree.ui.fragment.verification

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
class VerificationViewModel @Inject constructor(private val useCase: AuthUseCase) :
    ViewModel() {
    private val _state = MutableStateFlow(VerificationState())
    val state = _state.asStateFlow()

    fun verifyAccount(email: String, otp: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    verificationResponse = null,
                    resendVerifyResponse = null,
                    isLoading = true,
                    error = null
                )
            }

            useCase.verifyAccount(email, otp).apply {
                _state.update {
                    it.copy(
                        verificationResponse = data,
                        isLoading = false,
                        error = message
                    )
                }
            }
        }
    }

    fun resendVerifyOTP(email: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    resendVerifyResponse = null,
                    verificationResponse = null,
                    isLoading = true,
                    error = null
                )
            }

            useCase.resendVerifyOTP(email).apply {
                _state.update {
                    it.copy(
                        resendVerifyResponse = data,
                        isLoading = false,
                        error = message
                    )
                }
            }
        }
    }

    data class VerificationState(
        val verificationResponse: VerificationResponse? = null,
        val resendVerifyResponse: VerificationResponse? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )
}