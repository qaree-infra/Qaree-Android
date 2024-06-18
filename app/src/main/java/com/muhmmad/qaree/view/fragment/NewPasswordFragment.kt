package com.muhmmad.qaree.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.FragmentNewPasswordBinding
import com.muhmmad.qaree.view.activity.AuthActivity
import com.muhmmad.qaree.viewModel.NewPasswordViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewPasswordFragment : Fragment() {
    private val binding: FragmentNewPasswordBinding by lazy {
        FragmentNewPasswordBinding.inflate(layoutInflater)
    }
    private val activity: AuthActivity by lazy {
        getActivity() as AuthActivity
    }
    private val nav: NavController by lazy {
        findNavController()
    }
    private val viewModel: NewPasswordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            //checkStatus
            checkState()
            val token: String = arguments?.getString("token").toString()
            btnCreateNewPass.setOnClickListener {
                val pass = layoutPassword.editText?.text.toString()
                val repeatPass = layoutConPassword.editText?.text.toString()
                if (checkValidation(pass, repeatPass)) viewModel.newPassword(pass, token)
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
        } else binding.layoutPassword.error = null

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
        } else binding.layoutConPassword.error = null
        return true
    }

    private fun checkState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                if (it.isLoading) activity.showLoading(binding.root)
                else activity.dismissLoading(binding.root)

                if (it.error?.isNotEmpty() == true) activity.showError(
                    binding.root,
                    it.error.toString()
                )
                else if (it.newPasswordResponse != null) {
                    activity.showMessage(it.newPasswordResponse.message.toString())
                    nav.navigate(R.id.action_newPasswordFragment_to_loginFragment)
                }
            }
        }
    }
}