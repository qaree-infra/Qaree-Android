package com.muhmmad.qaree.ui.fragment.new_password

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.FragmentNewPasswordBinding
import com.muhmmad.qaree.ui.fragment.forget_password.ForgotPasswordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewPasswordFragment : Fragment() {
    private val binding: FragmentNewPasswordBinding by lazy {
        FragmentNewPasswordBinding.inflate(layoutInflater)
    }
    private val viewModel: NewPasswordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnCreateNewPass.setOnClickListener {
                val pass = layoutPassword.editText?.text.toString()
                val repeatPass = layoutConPassword.editText?.text.toString()
                if (checkValidation(pass, repeatPass)) {

                }
            }
        }
    }

    private fun checkValidation(pass: String, repeatPass: String): Boolean {
        if (pass.isEmpty()) {
            binding.layoutPassword.error = getString(R.string.please_enter_the_password)
            return false
        } else if (pass.length < 6) {
            binding.layoutPassword.error = getString(R.string.the_password_is_not_valid)
            return false
        } else {
            binding.layoutPassword.error = null
        }

        if (repeatPass.isEmpty()) {
            binding.layoutConPassword.error = getString(R.string.please_enter_the_password)
            return false
        } else if (repeatPass.length < 6) {
            binding.layoutConPassword.error = getString(R.string.the_password_is_not_valid)
            return false
        } else if (pass != repeatPass) {
            binding.layoutConPassword.error =
                getString(R.string.the_password_is_not_matches_the_confirmation_password)
            return false
        } else {
            binding.layoutConPassword.error = null
        }
        return true
    }
}