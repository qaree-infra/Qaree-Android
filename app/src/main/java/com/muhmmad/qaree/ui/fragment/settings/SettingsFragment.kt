package com.muhmmad.qaree.ui.fragment.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private val binding: FragmentSettingsBinding by lazy {
        FragmentSettingsBinding.inflate(layoutInflater)
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
            handleViews
        }
    }

    private val handleViews: () -> Unit
        get() = {
            binding.apply {
                ivBack.setOnClickListener {
                    nav.navigateUp()
                }
                tvEditProfile.setOnClickListener {
                    nav.navigate(R.id.action_settingsFragment_to_editProfileFragment)
                }
                tvChangePass.setOnClickListener {

                }
                tvPayment.setOnClickListener {

                }
                tvDeleteAccount.setOnClickListener {

                }
                tvLanguage.setOnClickListener {

                }
                tvPrivacyPolicy.setOnClickListener {

                }
                tvAboutUs.setOnClickListener {

                }
                tvLogout.setOnClickListener {

                }
            }
        }
}