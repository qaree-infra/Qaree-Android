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
import com.muhmmad.qaree.databinding.FragmentVerificationBinding
import com.muhmmad.qaree.view.activity.AuthActivity
import com.muhmmad.qaree.viewModel.VerificationViewModel
import dagger.hilt.android.AndroidEntryPoint
import `in`.aabhasjindal.otptextview.OTPListener
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VerificationFragment : Fragment() {
    private val binding: FragmentVerificationBinding by lazy {
        FragmentVerificationBinding.inflate(layoutInflater)
    }
    private val activity: AuthActivity by lazy {
        getActivity() as AuthActivity
    }
    private val nav: NavController by lazy {
        findNavController()
    }
    private val viewModel: VerificationViewModel by viewModels()
    private var isForgetPassword: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            //checkStatus
            checkStatus()
            val email: String = arguments?.getString("email", "").toString()
            isForgetPassword = arguments?.getBoolean("forgetPassword", false) == true
            tvEmail.text = email

            otpView.otpListener = object : OTPListener {
                override fun onInteractionListener() {
                }

                override fun onOTPComplete(otp: String) {
                    if (isForgetPassword) viewModel.validateOTPPassword(
                        email,
                        otpView.otp.toString()
                    )
                    else viewModel.verifyAccount(email, otpView.otp.toString())
                }
            }

            btnVerify.setOnClickListener {
                if (otpView.otp?.length!! < 6) {
                    activity.showError(binding.root, getString(R.string.the_otp_is_not_valid))
                } else {
                    if (isForgetPassword) viewModel.validateOTPPassword(
                        email,
                        otpView.otp.toString()
                    )
                    else viewModel.verifyAccount(email, otpView.otp.toString())
                }
            }

            tvResendCode.setOnClickListener {
                if (isForgetPassword) viewModel.resendOTPPassword(email)
                else viewModel.resendVerifyOTP(email)
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
                else if (it.verificationResponse != null) {
                    activity.showMessage(it.verificationResponse.message.toString())
                    if (isForgetPassword) {
                        val bundle = Bundle()
                        bundle.putString("token", "Bearer ${it.verificationResponse.reset_token}")
                        nav.navigate(
                            R.id.action_verificationFragment_to_newPasswordFragment,
                            bundle
                        )
                    } else nav.navigate(R.id.action_verificationFragment_to_loginFragment)
                } else if (it.resendVerifyResponse != null) {
                    activity.showMessage(it.resendVerifyResponse.message.toString())
                }
            }
        }
    }
}