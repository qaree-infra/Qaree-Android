package com.muhmmad.qaree.view.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.muhmmad.qaree.BuildConfig
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.FragmentLoginBinding
import com.muhmmad.qaree.view.activity.AuthActivity
import com.muhmmad.qaree.viewModel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private val binding: FragmentLoginBinding by lazy {
        FragmentLoginBinding.inflate(layoutInflater)
    }
    private val activity: AuthActivity by lazy {
        getActivity() as AuthActivity
    }
    private val ctx: Context by lazy {
        binding.root.context
    }
    private val nav: NavController by lazy {
        findNavController()
    }
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            //checkStatus
            checkStatus()
            tvForgotPassword.setOnClickListener {
                nav.navigate(R.id.action_loginFragment_to_forgetPasswordFragment)
            }
            btnSignIn.setOnClickListener {
                val email = layoutEmail.editText?.text.toString()
                val pass = layoutPassword.editText?.text.toString()
                if (checkValidation(email, pass)) viewModel.login()
            }
            btnGoogle.setOnClickListener {
                loginWithGoogle()
            }
            btnFacebook.setOnClickListener {

            }
            tvSignUp.setOnClickListener {
                nav.navigate(R.id.action_loginFragment_to_registerFragment)
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
                if (it.goHome) activity.goToHome(ctx)
                if (it.isLoading) activity.showLoading(binding.root)
                else activity.dismissLoading(binding.root)

                if (it.error?.isNotEmpty() == true) activity.showError(
                    binding.root,
                    it.error.toString()
                )
                else if (it.loginResponse != null) {
                    if (it.loginResponse.token.isNotEmpty()) {
                        viewModel.saveToken("Bearer ${it.loginResponse.token}")
                        viewModel.getUserData()
                    }
                }
            }
        }
    }

    private fun checkValidation(email: String, pass: String): Boolean {
        if (email.isEmpty()) {
            binding.layoutEmail.error = getString(R.string.please_enter_the_e_mail)
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.layoutEmail.error = getString(R.string.e_mail_address_is_not_valid)
            return false
        } else binding.layoutEmail.error = null

        if (pass.isEmpty()) {
            binding.layoutPassword.error = getString(R.string.please_enter_the_password)
            return false
        } else if (pass.length < 6) {
            binding.layoutPassword.error = getString(R.string.the_password_is_not_valid)
            return false
        } else binding.layoutPassword.error = null
        return true
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                handleSignInResult(
                    GoogleSignIn.getSignedInAccountFromIntent(
                        result.data
                    )
                )
            } else activity.showError(binding.root, getString(R.string.login_with_google_error))
        }

    private fun loginWithGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.googleWebClientID)
            .requestEmail()
            .requestProfile()
            .requestServerAuthCode(BuildConfig.googleWebClientID).build()

        val googleSignInClient = GoogleSignIn.getClient(activity, gso)
        googleSignInClient.signOut()
        launcher.launch(googleSignInClient.signInIntent)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) = try {
        val account = completedTask.getResult(ApiException::class.java)
        viewModel.loginWithGoogle(account.idToken!!)
    } catch (e: Exception) {
        activity.showError(binding.root, getString(R.string.login_with_google_error))
    }

    override fun onResume() {
        super.onResume()
        binding.layoutEmail.editText?.setText(viewModel.email.value)
        binding.layoutPassword.editText?.setText(viewModel.password.value)
    }
}