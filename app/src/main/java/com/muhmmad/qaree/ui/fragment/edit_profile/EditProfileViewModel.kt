package com.muhmmad.qaree.ui.fragment.edit_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhmmad.domain.usecase.AuthUseCase
import com.muhmmad.domain.usecase.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val useCase: HomeUseCase,
    private val authUseCase: AuthUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(EditProfileState())
    val state = _state.asStateFlow()

    fun setUpdateAvatarType(type: UpdateImagesType) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    updateAvatarType = type
                )
            }
        }
    }


    data class EditProfileState(
        val isLoading: Boolean = false,
        val error: String? = null,
        val updateAvatarType: UpdateImagesType? = null
    )

    enum class UpdateImagesType {
        CAMERA,
        GALLERY
    }
}