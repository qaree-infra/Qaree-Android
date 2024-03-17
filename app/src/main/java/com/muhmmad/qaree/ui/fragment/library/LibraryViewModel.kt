package com.muhmmad.qaree.ui.fragment.library

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhmmad.domain.model.BaseResponse
import com.muhmmad.domain.model.LibraryResponse
import com.muhmmad.domain.usecase.AuthUseCase
import com.muhmmad.domain.usecase.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val useCase: HomeUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(LibraryState())
    val state = _state.asStateFlow()

    fun getLibrary() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(
                    isLoading = true,
                    error = null,
                    libraryResponse = null
                )
            }
            useCase.getLibrary(authUseCase.getToken()).apply {
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

    fun createShelf(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(
                    isLoading = true,
                    error = null,
                )
            }
            useCase.createShelf(name, authUseCase.getToken()).apply {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = message,
                        createShelfResponse = data
                    )
                }
            }
        }
    }


    data class LibraryState(
        val error: String? = null,
        val isLoading: Boolean = false,
        val libraryResponse: LibraryResponse? = null,
        val createShelfResponse: BaseResponse? = null
    )

}