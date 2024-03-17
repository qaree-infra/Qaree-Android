package com.muhmmad.qaree.ui.fragment.shelf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhmmad.domain.model.BaseResponse
import com.muhmmad.domain.model.ShelfResponse
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
class ShelfViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val useCase: HomeUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ShelfState())
    val state = _state.asStateFlow()

    fun getShelfDetails(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(isLoading = true, error = null, removeBook = null)
            }
            useCase.getShelfDetails(token = authUseCase.getToken(), name = id).apply {
                _state.update {
                    it.copy(isLoading = false, error = message, shelfResponse = data)
                }
            }
        }
    }

    fun removeBookFromShelf(bookId: String, shelfId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(isLoading = true, error = null)
            }
            useCase.removeBookFromShelf(bookId = bookId, shelfId = shelfId, authUseCase.getToken())
                .apply {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = message,
                            removeBook = data,
                        )
                    }
                }
        }
    }

    data class ShelfState(
        val removeBook: BaseResponse? = null,
        val shelfResponse: ShelfResponse? = null,
        val isLoading: Boolean = false,
        val error: String? = null,
    )
}