package com.muhmmad.qaree.ui.activity.reading_view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.muhmmad.domain.model.BookChapter
import com.muhmmad.domain.model.BookContent
import com.muhmmad.domain.model.ContentItem
import com.muhmmad.domain.usecase.AuthUseCase
import com.muhmmad.domain.usecase.ReadingVIewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadingViewViewModel @Inject constructor(
    private val useCase: ReadingVIewUseCase,
    private val authUseCase: AuthUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ReadingViewState())
    val state = _state.asStateFlow()

    private val _bookContent = MutableStateFlow<BookContent?>(null)
    val bookContent = _bookContent.asStateFlow()
    val token = MutableStateFlow<String?>(null)

    init {
        getToken()
    }

    private fun getToken() {
        viewModelScope.launch {
            authUseCase.getToken().apply {
                token.update {
                    this
                }
            }
        }
    }


    fun getBookContent(bookId: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            useCase.getBookContent(bookId).apply {
                _state.update { it.copy(isLoading = false, error = message) }
                _bookContent.update { data }
            }
        }
    }

    fun getChapter(
        token: String,
        bookId: String,
        chaptersList: List<ContentItem>
    ): Flow<PagingData<BookChapter>> {
        return Pager(
            // Configure how data is loaded by passing additional properties to
            // PagingConfig, such as prefetchDistance.
            PagingConfig(pageSize = 1)
        ) {
            BookPagingSource(useCase, chaptersList, bookId, token)
        }.flow.cachedIn(viewModelScope)
    }

    data class ReadingViewState(
        val isLoading: Boolean = false,
        val error: String? = null,
    )
}
