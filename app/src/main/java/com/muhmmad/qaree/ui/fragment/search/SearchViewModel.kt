package com.muhmmad.qaree.ui.fragment.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhmmad.domain.model.BooksResponse
import com.muhmmad.domain.model.CategoriesResponse
import com.muhmmad.domain.usecase.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val useCase: HomeUseCase) : ViewModel() {
    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()

    fun search(text: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            useCase.search(text).apply {
                _state.update {
                    it.copy(error = message, isLoading = false, searchResponse = data)
                }
            }
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            useCase.getCategories().apply {
                _state.update {
                    it.copy(isLoading = false, error = message, categoriesResponse = data)
                }
            }
        }
    }

    data class SearchState(
        val categoriesResponse: CategoriesResponse? = null,
        val isLoading: Boolean = false,
        val error: String? = null,
        val searchResponse: BooksResponse? = null
    )
}