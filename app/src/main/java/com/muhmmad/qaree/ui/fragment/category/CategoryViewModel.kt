package com.muhmmad.qaree.ui.fragment.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhmmad.domain.model.BooksResponse
import com.muhmmad.domain.usecase.BookUseCase
import com.muhmmad.domain.usecase.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val useCase: BookUseCase) : ViewModel() {

    private val _state = MutableStateFlow(CategoryState())
    val state = _state.asStateFlow()

    fun getBooksByCategory(categoryId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }
            useCase.getBooksByCategory(categoryId).apply {
                _state.update { it.copy(isLoading = false, error = message, response = data) }
            }
        }
    }


    data class CategoryState(
        val isLoading: Boolean = false,
        val error: String? = null,
        val response: BooksResponse? = null
    )
}