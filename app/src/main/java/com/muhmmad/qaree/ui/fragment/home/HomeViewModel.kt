package com.muhmmad.qaree.ui.fragment.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhmmad.domain.model.ActivitiesResponse
import com.muhmmad.domain.model.Author
import com.muhmmad.domain.model.AuthorsResponse
import com.muhmmad.domain.model.Book
import com.muhmmad.domain.model.BooksResponse
import com.muhmmad.domain.model.CategoriesResponse
import com.muhmmad.domain.model.Category
import com.muhmmad.domain.model.Cover
import com.muhmmad.domain.model.Offer
import com.muhmmad.domain.model.OffersResponse
import com.muhmmad.domain.usecase.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: HomeUseCase) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val author = Author(id = "", name = "author name", avatar = "")
            val book = Book(
                price = 50.0,
                name = "Book name",
                author = author,
                cover = Cover(),
                id = "",
                categories = listOf(Category("", "", "", ""))
            )
            val offer = Offer(percent = 20, expireAt = "", book = book)

            val category = Category(id = "", nameEn = "Category", image = "", nameAr = "")
            _state.update {
                it.copy(
                    isLoading = true,
                    offersResponse = OffersResponse(listOf(offer, offer)),
                    activitiesResponse = ActivitiesResponse(
                        image = "",
                        "book Name",
                        60,
                        "150",
                        "70"
                    ),
                    authorsResponse = AuthorsResponse(
                        listOf(
                            author,
                            author,
                            author,
                            author,
                            author,
                            author,
                            author
                        )
                    ),
                    categoriesResponse = CategoriesResponse(
                        listOf(
                            category, category, category, category, category, category,
                        )
                    ),
                    newReleasesResponse = BooksResponse(
                        listOf(
                            book,
                            book,
                            book,
                            book,
                            book,
                            book,
                            book
                        )
                    ),
                    bestSellerResponse = BooksResponse(
                        listOf(
                            book,
                            book,
                            book,
                            book,
                            book,
                            book,
                            book
                        )
                    ),
                )
            }
        }
    }

    fun getOffers() {
        viewModelScope.launch {
            useCase.getOffers().apply {
                _state.update {
                    it.copy(
                        offersResponse = data,
                        error = message,
                        isLoading = false
                    )
                }
            }
        }
    }

    data class HomeState(
        val offersResponse: OffersResponse? = null,
        val activitiesResponse: ActivitiesResponse? = null,
        val authorsResponse: AuthorsResponse? = null,
        val categoriesResponse: CategoriesResponse? = null,
        val newReleasesResponse: BooksResponse? = null,
        val bestSellerResponse: BooksResponse? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )
}