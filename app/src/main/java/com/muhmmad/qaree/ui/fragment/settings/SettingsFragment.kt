package com.muhmmad.qaree.ui.fragment.settings

import android.app.UiModeManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_UNSPECIFIED
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.muhmmad.domain.model.AppMode
import com.muhmmad.domain.model.User
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.FragmentSettingsBinding
import com.muhmmad.qaree.ui.activity.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "SettingsFragment"

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
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
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
                    "user", User::class.java
                ) ?: User(_id = "", name = "") else arguments?.getParcelable("user")

            when (AppCompatDelegate.getDefaultNightMode()) {
                MODE_NIGHT_YES, MODE_NIGHT_UNSPECIFIED -> switchMode.isChecked = true
                else -> switchMode.isChecked = false
            }

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
                nav.navigate(R.id.action_settingsFragment_to_editCardsBottomSheetDialogFragment)
            }
            tvDeleteAccount.setOnClickListener {
                nav.navigate(R.id.action_settingsFragment_to_deleteAccountDialog)
            }
            switchMode.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
                    viewModel.changeUiMode(AppMode.DARK)
                } else {
                    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
                    viewModel.changeUiMode(AppMode.LIGHT)
                }
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