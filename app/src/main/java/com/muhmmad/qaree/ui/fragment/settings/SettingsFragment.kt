package com.muhmmad.qaree.ui.fragment.settings

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.muhmmad.domain.model.Book
import com.muhmmad.domain.model.User
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
            handleViews()
        }
    }

    private fun handleViews() {
        binding.apply {
            val user =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) arguments?.getSerializable(
                    "user",
                    User::class.java
                ) ?: User(_id = "", name = "") else arguments?.getSerializable("user") as User
            ivBack.setOnClickListener {
                nav.navigateUp()
            }
            tvEditProfile.setOnClickListener {
                val bundle = Bundle()
                bundle.putSerializable("user", user)
                nav.navigate(R.id.action_settingsFragment_to_editProfileFragment, bundle)
            }
            tvChangePass.setOnClickListener {
                nav.navigate(R.id.action_settingsFragment_to_changePasswordFragment)
            }
            tvPayment.setOnClickListener {

            }
            tvDeleteAccount.setOnClickListener {

            }
            switchMode.setOnCheckedChangeListener { buttonView, isChecked ->

            }
            switchNotification.setOnCheckedChangeListener { buttonView, isChecked ->

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