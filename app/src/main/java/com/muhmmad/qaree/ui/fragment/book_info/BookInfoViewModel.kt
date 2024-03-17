package com.muhmmad.qaree.ui.fragment.book_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhmmad.domain.model.BaseResponse
import com.muhmmad.domain.model.ReviewsResponse
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
class BookInfoViewModel @Inject constructor(
    private val useCase: HomeUseCase,
    private val authUseCase: AuthUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(BookInfoState())
    val state = _state.asStateFlow()
    fun getReviews(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }
            useCase.getBookReviews(id).apply {
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

    fun makeReview(bookId: String, rate: Float, content: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }
            useCase.makeReview(
                token = authUseCase.getToken(),
                bookId = bookId,
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

    data class BookInfoState(
        val makeReviewResponse: BaseResponse? = null,
        val reviewsResponse: ReviewsResponse? = null,
        val isLoading: Boolean = false,
        val error: String? = null,
    )
}