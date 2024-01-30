package com.muhmmad.qaree

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.muhmmad.qaree.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private val binding: FragmentLoginBinding by lazy {
        FragmentLoginBinding.inflate(layoutInflater)
    }
    private val nav: NavController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            tvForgotPassword.setOnClickListener {
                nav.navigate(R.id.action_loginFragment_to_newPasswordFragment)
            }
            tvSignIn.setOnClickListener {

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
}