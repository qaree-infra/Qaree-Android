package com.muhmmad.qaree.view.dialog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.muhmmad.qaree.databinding.DeleteAccountDialogBinding
import com.muhmmad.qaree.view.activity.AuthActivity
import com.muhmmad.qaree.viewModel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DeleteAccountDialog : DialogFragment() {
    private val binding: DeleteAccountDialogBinding by lazy {
        DeleteAccountDialogBinding.inflate(layoutInflater)
    }
    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            checkState()
            tvDelete.setOnClickListener {
                viewModel.deleteAccount()
            }

            btnCancel.setOnClickListener {
                this@DeleteAccountDialog.dismiss()
            }
        }
    }

    private fun checkState() {
        lifecycleScope.launch {
            viewModel.state.collectLatest {
                if (it.deleteAccount?.success == true) {
                    startActivity(Intent(binding.root.context, AuthActivity::class.java))
                    activity?.finish()
                }
            }
        }
    }
}