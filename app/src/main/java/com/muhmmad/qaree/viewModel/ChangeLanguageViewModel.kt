package com.muhmmad.qaree.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhmmad.domain.model.Language
import com.muhmmad.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeLanguageViewModel @Inject constructor(private val userUseCase: UserUseCase) :
    ViewModel() {
    private val _language = MutableStateFlow<Language?>(null)
    val language = _language.asStateFlow()

    fun getLanguage() = viewModelScope.launch {
        userUseCase.getLanguage()
    }

    fun changeLanguage(context: Context, lang: Language) = viewModelScope.launch {
      //  LocaleHelper.setLocale(context, lang.name)
        userUseCase.changeLanguage(lang).apply {
            _language.emit(lang)
        }
    }
}