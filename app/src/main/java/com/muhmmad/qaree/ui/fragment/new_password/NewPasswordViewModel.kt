package com.muhmmad.qaree.ui.fragment.new_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NewPasswordViewModel : ViewModel() {
    fun newPassword(pass: String) {
        viewModelScope.launch {
            TODO("the new password code")
        }
    }
}