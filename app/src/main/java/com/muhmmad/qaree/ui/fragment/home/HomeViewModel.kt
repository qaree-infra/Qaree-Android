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
import com.muhmmad.domain.model.Offer
import com.muhmmad.domain.model.OffersResponse
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val offer = Offer(
                id = 0,
                image = "",
                bookName = "Book Name",
                authorName = "Author Name",
                price = "500",
                percentage = "30%"
            )
            val author = Author(image = "", name = "Author", id = 0)
            val category = Category(id = 0, name = "Category", image = "")
            val book = Book(
                id = 0,
                image = "",
                rate = 6f,
                authorName = "Author name",
                bookName = "book name"
            )
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
                    newReleasesResponse = BooksResponse(listOf(book,book,book,book,book,book,book)),
                    bestSellerResponse = BooksResponse(listOf(book,book,book,book,book,book,book)),
                )
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