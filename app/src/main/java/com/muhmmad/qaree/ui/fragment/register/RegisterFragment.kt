package com.muhmmad.qaree.ui.fragment.register

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.FragmentRegisterBinding
import com.muhmmad.qaree.ui.activity.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private val activity: AuthActivity by lazy {
        getActivity() as AuthActivity
    }
    private val binding: FragmentRegisterBinding by lazy {
        FragmentRegisterBinding.inflate(layoutInflater)
    }
    private val nav: NavController by lazy {
        findNavController()
    }
    private val viewModel: RegisterViewModel by viewModels()

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
            btnSignUp.setOnClickListener {
                val name = layoutName.editText?.text.toString()
                val email = layoutEmail.editText?.text.toString()
                val pass = layoutPassword.editText?.text.toString()
                if (checkValidation(name, email, pass)) {
                    viewModel.register()
                }
            }
            rlFacebook.setOnClickListener {

            }
            rlGoogle.setOnClickListener {

            }
            tvSignIn.setOnClickListener {
                nav.navigate(R.id.action_registerFragment_to_loginFragment)
            }
            layoutName.editText?.addTextChangedListener {
                viewModel.updateName(it.toString())
            }
            layoutEmail.editText?.addTextChangedListener {
                viewModel.updateEmail(it.toString())
            }
            layoutPassword.editText?.addTextChangedListener {
                viewModel.updatePassword(it.toString())
            }
        }
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
                else if (it.registerResponse != null) {
                    val bundle = Bundle()
                    bundle.putString("email", binding.layoutEmail.editText?.text.toString())
                    nav.navigate(R.id.action_registerFragment_to_verificationFragment, bundle)
                }
            }
        }
    }

    private fun checkValidation(name: String, email: String, pass: String): Boolean {
        if (name.isEmpty()) {
            binding.layoutName.error = getString(R.string.please_enter_your_name)
            return false
        } else if (name.length < 8) {
            binding.layoutName.error = getString(R.string.please_enter_your_full_name)
            return false
        } else {
            binding.layoutName.error = null
        }

        if (email.isEmpty()) {
            binding.layoutEmail.error = getString(R.string.please_enter_the_e_mail)
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
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

    override fun onResume() {
        super.onResume()
        binding.layoutName.editText?.setText(viewModel.name.value)
        binding.layoutEmail.editText?.setText(viewModel.email.value)
        binding.layoutPassword.editText?.setText(viewModel.password.value)
    }

    override fun onStop() {
        viewModel.destroy()
        super.onStop()
    }
}