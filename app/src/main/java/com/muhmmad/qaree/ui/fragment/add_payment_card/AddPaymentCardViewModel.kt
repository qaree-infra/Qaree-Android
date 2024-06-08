package com.muhmmad.qaree.ui.fragment.add_payment_card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhmmad.domain.model.Card
import com.muhmmad.domain.usecase.UserUseCase
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject


@HiltViewModel
class AddPaymentCardViewModel @Inject constructor(private val useCase: UserUseCase) : ViewModel() {
    val expiryMonth = MutableStateFlow("")
    val expiryYear = MutableStateFlow("")
    val expiryDate = expiryMonth.combine(expiryYear) { month, year ->
        "$month/$year"
    }

    fun addPaymentCard(card: Card) = viewModelScope.launch {
        useCase.addPaymentCard(card)
    }
}