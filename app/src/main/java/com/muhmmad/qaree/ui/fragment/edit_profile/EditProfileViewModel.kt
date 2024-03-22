package com.muhmmad.qaree.ui.fragment.edit_profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhmmad.domain.model.User
import com.muhmmad.domain.usecase.AuthUseCase
import com.muhmmad.domain.usecase.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject
private const val TAG = "EditProfileViewModel"

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

    fun updateUserName(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.updateUserName(authUseCase.getToken(), text).apply {
                _state.update {
                    it.copy(
                        userData = data,
                        error = message
                    )
                }
            }
        }
    }

    fun updateUserBio(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.updateUserBio(authUseCase.getToken(), text).apply {
                _state.update {
                    it.copy(
                        userData = data,
                        error = message
                    )
                }
            }
        }
    }

    fun uploadUserAvatar(body: MultipartBody.Part) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.uploadUserAvatar(authUseCase.getToken(), body).apply {
                Log.i(TAG, data.toString())
                Log.i(TAG, message.toString())
            }
        }
    }


    data class EditProfileState(
        val isLoading: Boolean = false,
        val error: String? = null,
        val updateAvatarType: UpdateImagesType? = null,
        val userData: User? = null
    )

    enum class UpdateImagesType {
        CAMERA,
        GALLERY
    }
}