package com.muhmmad.qaree.view.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.muhmmad.domain.model.Language
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.ChangeLanguageBottomSheetDialogBinding
import com.muhmmad.qaree.viewModel.ChangeLanguageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChangeLanguageBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private val binding: ChangeLanguageBottomSheetDialogBinding by lazy {
        ChangeLanguageBottomSheetDialogBinding.inflate(layoutInflater)
    }

    private val viewModel: ChangeLanguageViewModel by viewModels()
    private val ctx: Context by lazy {
        binding.root.context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel.getLanguage()
            lifecycleScope.launch {
                viewModel.language.collectLatest {
                    it?.let {
                        when (it) {
                            Language.ENGLISH -> btnEnglish.isChecked = true
                            Language.ARABIC -> btnArabic.isChecked = true
                        }
                    }
                }

                btnSubmit.setOnClickListener {
                    when (radioGroup.checkedRadioButtonId) {
                        R.id.btn_english -> viewModel.changeLanguage(ctx, Language.ENGLISH)
                        R.id.btn_arabic -> viewModel.changeLanguage(ctx, Language.ARABIC)
                    }
                }
                tvCancel.setOnClickListener {
                    this@ChangeLanguageBottomSheetDialogFragment.dismiss()
                }
            }
        }
    }
}