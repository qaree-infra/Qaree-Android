package com.muhmmad.qaree.ui.fragment.forget_password

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.FragmentForgetPasswordBinding
import com.muhmmad.qaree.ui.activity.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForgetPasswordFragment : Fragment() {
    private val binding: FragmentForgetPasswordBinding by lazy {
        FragmentForgetPasswordBinding.inflate(layoutInflater)
    }
    private val activity: AuthActivity by lazy {
        getActivity() as AuthActivity
    }
    private val nav: NavController by lazy {
        findNavController()
    }
    private val viewModel: ForgotPasswordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            //checkStatus
            checkStatus()
            btnSubmit.setOnClickListener {
                val email = layoutEmail.editText?.text.toString()
                if (checkValidation(email)) {
                    viewModel.forgotPassword(email)
                }
            }
        }
    }

    private fun checkValidation(email: String): Boolean {
        if (email.isEmpty()) {
            binding.layoutEmail.error = getString(R.string.please_enter_the_e_mail)
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.layoutEmail.error = getString(R.string.e_mail_address_is_not_valid)
            return false
        } else {
            binding.layoutEmail.error = null
        }
        return true
    }

    private fun checkStatus() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                if (it.isLoading) activity.showLoading(binding.root)
                else activity.dismissLoading(binding.root)

                if (it.error?.isNotEmpty() == true) activity.showError(
                    binding.root,
                    it.error.toString()
                )
                else if (it.forgotPasswordResponse != null) {
                    val bundle = Bundle()
                    bundle.putBoolean("forgetPassword", true)
                    bundle.putString("email", binding.layoutEmail.editText?.text.toString())
                    activity.showMessage(it.forgotPasswordResponse.message.toString())
                    nav.navigate(R.id.action_forgetPasswordFragment_to_verificationFragment, bundle)
                }
            }
        }
    }
}