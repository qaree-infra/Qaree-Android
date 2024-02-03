package com.muhmmad.qaree.ui.fragment.login

import android.os.Bundle
import android.util.Log
import android.util.Patterns.EMAIL_ADDRESS
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.muhmmad.qaree.MainActivity
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private const val TAG = "LoginFragment"

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private val binding: FragmentLoginBinding by lazy {
        FragmentLoginBinding.inflate(layoutInflater)
    }
    private val activity: MainActivity by lazy {
        getActivity() as MainActivity
    }
    private val nav: NavController by lazy {
        findNavController()
    }
    private val viewModel: LoginViewModel by viewModels()

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
            tvForgotPassword.setOnClickListener {
                nav.navigate(R.id.action_loginFragment_to_newPasswordFragment)
            }
            btnSignIn.setOnClickListener {
                val email = layoutEmail.editText?.text.toString()
                val pass = layoutPassword.editText?.text.toString()
                if (checkValidation(email, pass)) {
                    viewModel.login(email, pass)
                }
            }
            btnGoogle.setOnClickListener {

            }
            btnFacebook.setOnClickListener {

            }
            tvSignUp.setOnClickListener {
                nav.navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }
    }

    private fun checkStatus() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                if (it.isLoading) activity.showLoading()
                else activity.dismissLoading()

                if (it.error?.isNotEmpty() == true) activity.showError(it.error.toString())
                else if (it.loginResponse != null) {
                    Log.i(TAG, it.loginResponse.toString())
                }
            }
        }
    }

    private fun checkValidation(email: String, pass: String): Boolean {
        if (email.isEmpty()) {
            binding.layoutEmail.error = getString(R.string.please_enter_the_e_mail)
            return false
        } else if (!EMAIL_ADDRESS.matcher(email).matches()) {
            binding.layoutEmail.error = getString(R.string.e_mail_address_is_not_valid)
            return false
        } else {
            binding.layoutEmail.error = null
        }

        if (pass.isEmpty()) {
            binding.layoutPassword.error = getString(R.string.please_enter_the_password)
            return false
        } else if (pass.length < 6) {
            binding.layoutPassword.error = getString(R.string.the_password_is_not_valid)
            return false
        } else {
            binding.layoutPassword.error = null
        }
        return true
    }
}