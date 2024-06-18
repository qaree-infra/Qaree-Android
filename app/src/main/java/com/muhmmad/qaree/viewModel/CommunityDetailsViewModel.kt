package com.muhmmad.qaree.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhmmad.domain.model.CommunityMembers
import com.muhmmad.domain.usecase.AuthUseCase
import com.muhmmad.domain.usecase.CommunityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityDetailsViewModel @Inject constructor(
    private val communityUseCase: CommunityUseCase,
    private val authUseCase: AuthUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(CommunityDetailsState())
    val state = _state.asSharedFlow()
    private val _communityMembers: MutableStateFlow<CommunityMembers?> =
        MutableStateFlow(null)
    val communityMembers = _communityMembers.asStateFlow()

    fun getCommunityMembers(bookId: String) = viewModelScope.launch(Dispatchers.IO) {
        _state.update { it.copy(isLoading = true) }
        communityUseCase.getCommunityMembers(
            bookId = bookId,
            page = 1,
            membersPerPage = 20,
            authUseCase.getToken()
        ).apply {
            _state.update { it.copy(isLoading = false, error = message) }
            _communityMembers.update { data }
        }
    }


    data class CommunityDetailsState(
        val isLoading: Boolean = false,
        val error: String? = null
    )
}