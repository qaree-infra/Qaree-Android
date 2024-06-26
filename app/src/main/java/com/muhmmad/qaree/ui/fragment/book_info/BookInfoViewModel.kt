package com.muhmmad.qaree.ui.fragment.book_info

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhmmad.domain.model.BaseResponse
import com.muhmmad.domain.model.Book
import com.muhmmad.domain.model.Card
import com.muhmmad.domain.model.LibraryResponse
import com.muhmmad.domain.model.PaymentOrder
import com.muhmmad.domain.model.ReviewsResponse
import com.muhmmad.domain.usecase.AuthUseCase
import com.muhmmad.domain.usecase.BookUseCase
import com.muhmmad.domain.usecase.CommunityUseCase
import com.muhmmad.domain.usecase.LibraryUseCase
import com.muhmmad.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookInfoViewModel @Inject constructor(
    private val bookUseCase: BookUseCase,
    private val authUseCase: AuthUseCase,
    private val libraryUseCase: LibraryUseCase,
    private val communityUseCase: CommunityUseCase,
    private val userUseCase: UserUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(BookInfoState())
    val state = _state.asSharedFlow()

    private val _bookState = MutableStateFlow(BookState.BUY)
    val bookState = _bookState.asStateFlow()

    private val _book = MutableStateFlow(Book())
    val book = _book.asStateFlow()

    private val _paymentOrder: MutableStateFlow<PaymentOrder?> = MutableStateFlow(null)
    val paymentOrder = _paymentOrder.asStateFlow()

    private val _reviewsResponse = MutableStateFlow<ReviewsResponse?>(null)
    val reviewsResponse = _reviewsResponse.asStateFlow()

    private val _libraryResponse = MutableStateFlow<LibraryResponse?>(null)
    val libraryResponse = _libraryResponse.asStateFlow()

    private val _paymentCards = MutableStateFlow<List<Card>?>(null)
    val paymentCards = _paymentCards.asStateFlow()

    private val _paymentCard = MutableStateFlow<Card?>(null)
    val paymentCard = _paymentCard.asStateFlow()

    private val _completePaymentResponse = MutableStateFlow<PaymentOrder?>(null)
    val completePaymentResponse = _completePaymentResponse.asSharedFlow()


    fun updateBook(book: Book?) {
        if (book != null) _book.update { book }
        getLibrary()
    }

    fun getBookStatus() = viewModelScope.launch(Dispatchers.IO) {
        _state.update { it.copy(isLoading = true) }
        bookUseCase.getBookStatus(authUseCase.getToken(), _book.value.id).apply {
            _state.update {
                it.copy(
                    isLoading = false,
                    error = message,
                )
            }

            data?.apply {
                if (status == null) {
                    if (_book.value.price < 1) _bookState.emit(BookState.START_READING)
                    else _bookState.emit(BookState.BUY)
                } else {
                    if (readingProgress == 0.0) _bookState.emit(BookState.START_READING)
                    else _bookState.emit(BookState.CONTINUE_READING)
                }
            }
        }
    }

    fun getReviews() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }
            bookUseCase.getBookReviews(_book.value.id).apply {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = message
                    )
                }

                _reviewsResponse.update {
                    data
                }
            }
        }
    }

    fun makeReview(rate: Float, content: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }
            bookUseCase.makeReview(
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
            libraryUseCase.getLibrary(token = authUseCase.getToken()).apply {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = message,
                    )
                }

                _libraryResponse.update { data }
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

    fun createPaymentOrder() = viewModelScope.launch(Dispatchers.IO) {
        _state.update { it.copy(isLoading = true) }
        bookUseCase.createPaymentOrder(authUseCase.getToken(), _book.value.id).apply {
            Log.i(TAG, message.toString())
            _state.update {
                _paymentOrder.emit(data)
                it.copy(isLoading = false, error = message)
            }
        }
    }

    fun joinCommunity() = viewModelScope.launch(Dispatchers.IO) {
        _state.update { it.copy(isLoading = true) }
        communityUseCase.joinCommunity(authUseCase.getToken(), _book.value.id).apply {
            _state.update {
                it.copy(isLoading = false, error = message, joinCommunityResponse = data)
            }
        }
    }

    fun completePayment(orderId: String) = viewModelScope.launch(Dispatchers.IO) {
        _state.update { it.copy(isLoading = true) }
        bookUseCase.completePaymentOrder(authUseCase.getToken(), _book.value.id, orderId).apply {
            _state.update {
                it.copy(
                    isLoading = false,
                    error = message,
                )
            }

            data?.let { _completePaymentResponse.emit(it) }
        }
    }

    fun getPaymentCards() = viewModelScope.launch {
        userUseCase.getPaymentCards().apply {
            _paymentCards.emit(this)
        }
    }

    fun setPaymentCard(card: Card) = viewModelScope.launch {
        _paymentCard.emit(card)
    }

    fun clearPaymentData() = viewModelScope.launch {
        _paymentOrder.emit(null)
        _paymentCard.emit(null)
    }

    data class BookInfoState(
        val error: String? = null,
        val isLoading: Boolean = false,
        val addBookToShelfResponse: BaseResponse? = null,
        val makeReviewResponse: BaseResponse? = null,
        val joinCommunityResponse: BaseResponse? = null,
    )

    enum class BookState {
        BUY,
        START_READING,
        CONTINUE_READING,
    }
}

private const val TAG = "BookInfoViewModel"