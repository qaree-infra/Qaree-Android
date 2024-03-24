package com.muhmmad.qaree.ui.fragment.change_password

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.FragmentChangePasswordBinding
import com.muhmmad.qaree.ui.activity.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChangePasswordFragment : Fragment() {
    private val binding: FragmentChangePasswordBinding by lazy {
        FragmentChangePasswordBinding.inflate(layoutInflater)
    }
    private val activity: HomeActivity by lazy {
        getActivity() as HomeActivity
    }
    private val nav: NavController by lazy {
        findNavController()
    }
    private val viewModel: ChangePasswordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            checkState()
            btnCreateNewPass.setOnClickListener {
                val oldPassword = layoutOldPassword.editText?.text.toString()
                val newPassword = layoutNewPassword.editText?.text.toString()
                val conNewPassword = layoutConPassword.editText?.text.toString()
                if (checkValidation(oldPassword, newPassword, conNewPassword)) {
                    viewModel.changePassword(oldPassword, newPassword)
                }
            }
        }
    }

    private fun checkValidation(
        oldPassword: String,
        newPassword: String,
        conNewPassword: String
    ): Boolean {
        if (oldPassword.isEmpty()) {
            binding.layoutOldPassword.error = getString(R.string.please_enter_your_old_password)
            return false
        } else {
            binding.layoutNewPassword.error = null
        }

        if (newPassword.isEmpty()) {
            binding.layoutNewPassword.error = getString(R.string.please_enter_your_new_password)
            return false
        } else if (newPassword.length < 6) {
            binding.layoutNewPassword.error =
                getString(R.string.the_password_should_not_be_less_than_6_letters)
            return false
        } else {
            binding.layoutNewPassword.error = null
        }

        if (conNewPassword != newPassword) {
            binding.layoutConPassword.error =
                getString(R.string.the_repeated_password_does_not_matches_the_password)
            return false
        } else {
            binding.layoutConPassword.error = null
        }
        return true
    }

    private fun checkState() {
        lifecycleScope.launch {
            viewModel.state.collect {
                if (it.isLoading) activity.showLoading(binding.root) else activity.dismissLoading(
                    binding.root
                )

                if (it.error?.isNotEmpty() == true) activity.showError(
                    binding.root,
                    it.error.toString()
                )

                if (it.response != null) {
                    activity.showMessage(getString(R.string.the_password_changed_successfully))
                    nav.navigateUp()
                }
            }
        }
    }
}