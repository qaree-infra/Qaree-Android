package com.muhmmad.qaree.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.muhmmad.qaree.databinding.FragmentNotificationsBinding
import com.muhmmad.qaree.view.activity.HomeActivity
import com.muhmmad.qaree.view.adapters.NotificationsAdapter
import com.muhmmad.qaree.viewModel.NotificationsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotificationsFragment : Fragment() {
    private val binding: FragmentNotificationsBinding by lazy {
        FragmentNotificationsBinding.inflate(layoutInflater)
    }
    private val nav: NavController by lazy {
        findNavController()
    }
    private val activity: HomeActivity by lazy {
        getActivity() as HomeActivity
    }
    private val adapter: NotificationsAdapter by lazy {
        NotificationsAdapter()
    }
    private val viewModel: NotificationsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            checkState()
            ivBack.setOnClickListener {
                nav.navigateUp()
            }
            rvNotifications.adapter = adapter
            viewModel.getNotifications()
        }
    }

    private fun checkState() {
        lifecycleScope.launch {
            viewModel.state.collectLatest {
                if (it.isLoading) activity.showLoading(binding.root) else activity.dismissLoading(
                    binding.root
                )
                it.error?.let { error -> activity.showError(binding.root, error) }
                it.notifications?.let { notifications ->
                    adapter.setData(notifications.notifications)
                }

                binding.tvNoNotifications.isVisible = it.notifications?.notifications?.isEmpty() ?: true
            }
        }
    }
}