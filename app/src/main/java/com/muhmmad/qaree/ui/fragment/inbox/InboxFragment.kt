package com.muhmmad.qaree.ui.fragment.inbox

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.muhmmad.qaree.databinding.FragmentInboxBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InboxFragment : Fragment() {
    private val binding: FragmentInboxBinding by lazy {
        FragmentInboxBinding.inflate(layoutInflater)
    }
    private val viewModel: InboxViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

        }
    }
}