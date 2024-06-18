package com.muhmmad.qaree.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.DialogEditNameBinding
import com.muhmmad.qaree.viewModel.EditProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditNameDialog : DialogFragment() {
    private val binding: DialogEditNameBinding by lazy {
        DialogEditNameBinding.inflate(layoutInflater)
    }

    private val viewModel: EditProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val isName: Boolean = arguments?.getBoolean("isName") == true
            edtName.setText(arguments?.getString("text").toString())
            if (isName) {
                tvEnterName.text =
                    getString(R.string.please_enter_your_name)
            } else {
                tvEnterName.text =
                    getString(R.string.enter_your_bio)
            }
            btnSubmit.setOnClickListener {
                val text = edtName.text.toString()
                if (checkValidation(isName, text)) {
                    if (isName) viewModel.updateUserName(text) else viewModel.updateUserBio(
                        text
                    )
                }
                this@EditNameDialog.dismiss()
            }

            btnCancel.setOnClickListener {
                this@EditNameDialog.dismiss()
            }
        }
    }

    private fun checkValidation(isName: Boolean, text: String): Boolean {
        if (isName) {
            if (text.isEmpty()) {
                binding.edtName.error = getString(R.string.please_enter_your_name)
                return false
            } else if (text.length < 6) {
                binding.edtName.error = getString(R.string.please_enter_your_full_name)
                return false
            }
        } else {
            if (text.isEmpty()) {
                binding.edtName.error = getString(R.string.please_enter_the_bio)
                return false
            } else if (text.length < 6) {
                binding.edtName.error = getString(R.string.th_bio_is_very_short)
                return false
            }
        }
        binding.edtName.error = null
        return true
    }
}