package com.muhmmad.qaree.ui.fragment.inbox

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhmmad.domain.usecase.CommunityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InboxViewModel @Inject constructor(private val communityUseCase: CommunityUseCase) :
    ViewModel() {
    private val _state = MutableStateFlow(InboxState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            communityUseCase.connectSocket()
        }
    }

    data class InboxState(
        val error: String? = null,
        val isLoading: Boolean = false,
    )
}