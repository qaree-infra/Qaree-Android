package com.muhmmad.qaree.ui.fragment.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhmmad.domain.model.BaseResponse
import com.muhmmad.domain.model.LibraryResponse
import com.muhmmad.domain.model.User
import com.muhmmad.domain.usecase.AuthUseCase
import com.muhmmad.domain.usecase.LibraryUseCase
import com.muhmmad.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val libraryUseCase: LibraryUseCase,
    private val useCase: UserUseCase,
    private val authUseCase: AuthUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()
    val userId = MutableStateFlow("")

    init {
        getUserId()
    }

    fun getProfileInfo(userId: String) = viewModelScope.launch {
        if (useCase.isUserProfile(userId)) getUserInfo()
        else getAuthorInfo(userId)
    }

    fun getUserInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }
            useCase.getUserInfo(authUseCase.getToken()).apply {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = message,
                        userDataResponse = data
                    )
                }
            }
        }
    }

    private fun getAuthorInfo(userId: String) = viewModelScope.launch(Dispatchers.IO) {
        _state.update { it.copy(isLoading = true) }
        useCase.getAuthorInfo(userId, authUseCase.getToken()).apply {
            _state.update {
                it.copy(
                    isLoading = false,
                    error = message,
                    userDataResponse = data
                )
            }
        }
    }

    private fun getUserId() = viewModelScope.launch {
        useCase.getUserId().apply {
            userId.update {
                this
            }
        }
    }

    fun getLibrary(userId: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }
            libraryUseCase.getLibrary(userId, authUseCase.getToken()).apply {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = message,
                        libraryResponse = data
                    )
                }
            }
        }
    }

    fun followAuthor(userId: String) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        useCase.followUser(authUseCase.getToken(), userId).apply {
            _state.update {
                it.copy(
                    isLoading = false,
                    error = message,
                    followResponse = data
                )
            }
        }
    }

    data class ProfileState(
        val libraryResponse: LibraryResponse? = null,
        val userDataResponse: User? = null,
        val isLoading: Boolean = false,
        val error: String? = null,
        val followResponse: BaseResponse? = null
    )
}