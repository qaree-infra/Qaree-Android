package com.muhmmad.qaree.ui.fragment.chat

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import coil.load
import com.muhmmad.domain.model.Book
import com.muhmmad.domain.model.Chat
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.FragmentChatBinding
import com.muhmmad.qaree.ui.activity.home.HomeActivity
import com.muhmmad.qaree.ui.fragment.CommunityViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ChatFragment : Fragment() {
    private val binding: FragmentChatBinding by lazy {
        FragmentChatBinding.inflate(layoutInflater)
    }
    private val nav: NavController by lazy {
        findNavController()
    }
    private val activity: HomeActivity by lazy {
        getActivity() as HomeActivity
    }
    private val viewModel: CommunityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            checkState()
            val chat: Chat = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) arguments?.getParcelable(
                    "chat",
                    Chat::class.java
                )!!
            else arguments?.getParcelable("chat")!!

            viewModel.getMessages(chat._id ?: "", CommunityViewModel.MessageType.UNREAD)
            initViews(chat)
        }
    }

    private fun initViews(chat: Chat) {
        binding.apply {
            ivChat.load(chat.getImage())
            tvChat.text = chat.getName()
            ivBack.setOnClickListener {
                nav.navigateUp()
            }
            ivMenu.setOnClickListener {

            }
            ivSend.setOnClickListener {

            }
        }
    }

    private fun checkState() {
        lifecycleScope.launch {
            viewModel.state.collectLatest {
                if (it.isLoading) activity.showLoading(binding.root)
                else activity.dismissLoading(binding.root)

                if (it.error?.isNotEmpty() == true) activity.showError(
                    binding.root,
                    it.error.toString()
                )
            }
        }
    }
}