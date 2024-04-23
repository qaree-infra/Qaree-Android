package com.muhmmad.qaree.ui.fragment.chat

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.muhmmad.domain.model.Book
import com.muhmmad.domain.model.Chat
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.FragmentChatBinding

class ChatFragment : Fragment() {
    private val binding: FragmentChatBinding by lazy {
        FragmentChatBinding.inflate(layoutInflater)
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
            val chat: Chat =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) arguments?.getSerializable(
                    "chat",
                    Chat::class.java
                )!! else arguments?.getSerializable("chat") as Chat



        }
    }
}