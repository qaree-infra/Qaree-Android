package com.muhmmad.qaree.ui.fragment.book_info

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhmmad.domain.model.BaseResponse
import com.muhmmad.domain.model.Book
import com.muhmmad.domain.model.BookStatus
import com.muhmmad.domain.model.LibraryResponse
import com.muhmmad.domain.model.PaymentOrder
import com.muhmmad.domain.model.ReviewsResponse
import com.muhmmad.domain.usecase.AuthUseCase
import com.muhmmad.domain.usecase.BookUseCase
import com.muhmmad.domain.usecase.LibraryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
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
                    if (status == null) _bookState.emit(BookState.BUY)
                    else {
                        if (readingProgress == 0.0) _bookState.emit(BookState.START_READING)
                        else _bookState.emit(BookState.CONTINUE_READING)
                    }
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

    //this function to get shelfs for add book to shelf dialog
    private fun getLibrary() {
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
        Log.i(TAG, shelfId.toString())
        Log.i(TAG, _book.value.id)
        libraryUseCase.addBookToShelf(authUseCase.getToken(), shelfId, _book.value.id).apply {
            _state.update {
                it.copy(isLoading = false, error = message, addBookToShelfResponse = data)
            }
        }

    }

    fun createPaymentOrder() = viewModelScope.launch(Dispatchers.IO) {
        _state.update { it.copy(isLoading = true) }
        useCase.createPaymentOrder(authUseCase.getToken(), _book.value.id).apply {
            _state.update {
                it.copy(isLoading = false, error = message, paymentOrder = data)
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
        val libraryResponse: LibraryResponse? = null,
        val paymentOrder: PaymentOrder? = null
    )

    enum class BookState {
        BUY,
        START_READING,
        CONTINUE_READING,
    }
}

private const val TAG = "BookInfoViewModel"