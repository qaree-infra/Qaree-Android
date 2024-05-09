package com.muhmmad.qaree.ui.fragment.community_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.FragmentCommunityDetailsBinding

class CommunityDetailsFragment : Fragment() {
    private val binding: FragmentCommunityDetailsBinding by lazy {
        FragmentCommunityDetailsBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

        }
    }
}