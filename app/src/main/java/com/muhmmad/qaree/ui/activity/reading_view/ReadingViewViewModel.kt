package com.muhmmad.qaree.ui.activity.reading_view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhmmad.domain.model.BookChapter
import com.muhmmad.domain.model.BookContent
import com.muhmmad.domain.usecase.AuthUseCase
import com.muhmmad.domain.usecase.ReadingVIewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "ReadingViewViewModel"

@HiltViewModel
class ReadingViewViewModel @Inject constructor(
    private val useCase: ReadingVIewUseCase,
    private val authUseCase: AuthUseCase
) :
    ViewModel() {
    private val _state = MutableStateFlow(ReadingViewState())
    val state = _state.asStateFlow()

    fun getBookContent(bookId: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            useCase.getBookContent(bookId).apply {
                _state.update { it.copy(isLoading = false, error = message, bookContent = data) }
            }
        }
    }

    fun getChapter(bookId: String, chapter: String) {
        viewModelScope.launch {
//            _state.update { it.copy(isLoading = true) }
//            useCase.getChapter(authUseCase.getToken(), bookId, chapter).apply {
//                _state.update {
//                    it.copy(
//                        isLoading = false,
//                        error = message,
//                        chapterContent = data
//                    )
//                }
//            }
        }
    }

    data class ReadingViewState(
        val isLoading: Boolean = false,
        val error: String? = null,
        val bookContent: BookContent? = null,
        val chapterContent: BookChapter? = null
    )
}
