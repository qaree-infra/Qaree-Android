package com.muhmmad.qaree.ui.fragment.book_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhmmad.domain.model.BaseResponse
import com.muhmmad.domain.model.Book
import com.muhmmad.domain.model.BookStatus
import com.muhmmad.domain.model.LibraryResponse
import com.muhmmad.domain.model.ReviewsResponse
import com.muhmmad.domain.usecase.AuthUseCase
import com.muhmmad.domain.usecase.BookUseCase
import com.muhmmad.domain.usecase.LibraryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookInfoViewModel @Inject constructor(
    private val useCase: BookUseCase,
    private val authUseCase: AuthUseCase,
    private val libraryUseCase: LibraryUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(BookInfoState())
    val state = _state.asStateFlow()

    private val _bookState = MutableStateFlow(BookState.BUY)
    val bookState = _bookState.asStateFlow()

    private val _book = MutableStateFlow(Book())
    val book = _book.asStateFlow()

    fun updateBook(book: Book) {
        _book.update { book }
        getLibrary()
    }

    fun getBookStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }
            useCase.getBookStatus(authUseCase.getToken(), _book.value.id).apply {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = message,
                        bookStatus = data
                    )
                }

                data?.apply {
                    if (status == "purchased" || book.value.price == 0.0) {
                        if (readingProgress == 0.0) _bookState.update { BookState.START_READING }
                        else _bookState.update { BookState.CONTINUE_READING }
                    } else _bookState.update { BookState.BUY }
                }
            }
        }
    }

    fun getReviews() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }
            useCase.getBookReviews(_book.value.id).apply {
                _state.update {
                    it.copy(
                        isLoading = false,
                        reviewsResponse = data,
                        error = message
                    )
                }
            }
        }
    }

    fun makeReview(rate: Float, content: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }
            useCase.makeReview(
                token = authUseCase.getToken(),
                bookId = _book.value.id,
                rate = rate,
                content = content
            ).apply {
                _state.update {
                    it.copy(
                        makeReviewResponse = data,
                        isLoading = false,
                        error = message
                    )
                }
            }
        }
    }

    fun getLibrary() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }
            libraryUseCase.getLibrary(authUseCase.getToken()).apply {
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

    fun addBookToShelf(shelfId: String) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        libraryUseCase.addBookToShelf(authUseCase.getToken(), shelfId, _book.value.id).apply {
            _state.update {
                it.copy(isLoading = false, error = message, addBookToShelfResponse = data)
            }
        }

    }

    data class BookInfoState(
        val addBookToShelfResponse: BaseResponse? = null,
        val bookStatus: BookStatus? = null,
        val makeReviewResponse: BaseResponse? = null,
        val reviewsResponse: ReviewsResponse? = null,
        val isLoading: Boolean = false,
        val error: String? = null,
        val libraryResponse: LibraryResponse? = null
    )

    enum class BookState {
        BUY,
        START_READING,
        CONTINUE_READING,
    }
}