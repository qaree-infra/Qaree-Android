package com.muhmmad.qaree

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.muhmmad.qaree.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private val binding: FragmentRegisterBinding by lazy {
        FragmentRegisterBinding.inflate(layoutInflater)
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
            btnSignUp.setOnClickListener {
                nav.navigate(R.id.action_registerFragment_to_verificationFragment)
            }
            btnFacebook.setOnClickListener {

            }
            btnGoogle.setOnClickListener {

            }
            tvSignIn.setOnClickListener {
                nav.navigateUp()
            }
        }
    }
}