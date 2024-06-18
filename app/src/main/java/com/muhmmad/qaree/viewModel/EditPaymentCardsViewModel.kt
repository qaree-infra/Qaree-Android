package com.muhmmad.qaree.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhmmad.domain.model.Card
import com.muhmmad.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditPaymentCardsViewModel @Inject constructor(private val useCase: UserUseCase) : ViewModel() {
    private val _cards = MutableStateFlow<List<Card>?>(null)
    val cards = _cards.asStateFlow()

    fun deleteCard(card: Card) = viewModelScope.launch {
        useCase.deletePaymentCard(card)
    }

    fun getCards() = viewModelScope.launch {
        useCase.getPaymentCards().apply {
            _cards.emit(this)
        }
    }
}