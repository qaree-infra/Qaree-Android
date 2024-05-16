package com.muhmmad.qaree.ui.fragment.settings

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.muhmmad.domain.model.User
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.FragmentSettingsBinding
import com.muhmmad.qaree.ui.activity.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private val binding: FragmentSettingsBinding by lazy {
        FragmentSettingsBinding.inflate(layoutInflater)
    }
    private val nav: NavController by lazy {
        findNavController()
    }
    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            checkState()
            handleViews()
        }
    }

    private fun handleViews() {
        binding.apply {
            val user =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) arguments?.getParcelable(
                    "user",
                    User::class.java
                ) ?: User(_id = "", name = "") else arguments?.getParcelable("user")
            ivBack.setOnClickListener {
                nav.navigateUp()
            }
            tvEditProfile.setOnClickListener {
                val bundle = Bundle()
                bundle.putParcelable("user", user)
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
                viewModel.logout()
            }
        }
    }

    private fun checkState() {
        lifecycleScope.launch {
            viewModel.state.collectLatest {
                if (it.isLogout) {
                    startActivity(Intent(binding.root.context, AuthActivity::class.java))
                    activity?.finish()
                }
            }
        }
    }
}