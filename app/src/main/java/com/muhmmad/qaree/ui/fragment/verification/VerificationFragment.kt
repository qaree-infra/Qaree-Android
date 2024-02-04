package com.muhmmad.qaree.ui.fragment.verification

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.FragmentVerificationBinding
import com.muhmmad.qaree.ui.activity.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import `in`.aabhasjindal.otptextview.OTPListener
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VerificationFragment : Fragment() {
    private val binding: FragmentVerificationBinding by lazy {
        FragmentVerificationBinding.inflate(layoutInflater)
    }
    private val activity: MainActivity by lazy {
        getActivity() as MainActivity
    }
    private val nav: NavController by lazy {
        findNavController()
    }
    private val viewModel: VerificationViewModel by viewModels()

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
            val email: String = arguments?.getString("email", "").toString()
            tvEmail.text = email


            otpView.otpListener = object : OTPListener {
                override fun onInteractionListener() {
                }

                override fun onOTPComplete(otp: String) {
                    viewModel.verifyAccount(email, otp)
                }
            }

            btnVerify.setOnClickListener {
                if (otpView.otp?.length!! < 6) {
                    activity.showError(getString(R.string.the_otp_is_not_valid))
                } else {
                    viewModel.verifyAccount(email, otpView.otp.toString())
                }
            }
        }
    }

    private fun checkStatus() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                if (it.isLoading) activity.showLoading()
                else activity.dismissLoading()

                if (it.error?.isNotEmpty() == true) activity.showError(it.error.toString())
                else if (it.verificationResponse != null) {
                    activity.showMessage(it.verificationResponse.message.toString())
                    nav.navigate(R.id.action_verificationFragment_to_loginFragment)
                }
            }
        }
    }
}