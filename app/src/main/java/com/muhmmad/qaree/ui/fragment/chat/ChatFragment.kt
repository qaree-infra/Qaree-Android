package com.muhmmad.qaree.ui.fragment.chat

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import coil.load
import com.muhmmad.domain.model.Message
import com.muhmmad.domain.model.Room
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.FragmentChatBinding
import com.muhmmad.qaree.ui.activity.home.HomeActivity
import com.muhmmad.qaree.ui.fragment.CommunityViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ChatFragment : Fragment(), OnClickListener {
    private val binding: FragmentChatBinding by lazy {
        FragmentChatBinding.inflate(layoutInflater)
    }
    private val nav: NavController by lazy {
        findNavController()
    }
    private val activity: HomeActivity by lazy {
        getActivity() as HomeActivity
    }
    private val adapter: ChatAdapter by lazy {
        ChatAdapter(
            //get userId from arguments or viewModel
            // if the user coming from ProfileFragment then userId will be from the arguments
            // else it will be from the viewModel
            viewModel.userId.value.ifEmpty { arguments?.getString("userId") ?: "" }
        )
    }
    private val viewModel: CommunityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            checkState()
            val room: Room =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) arguments?.getParcelable(
                    "chat",
                    Room::class.java
                )!!
                else arguments?.getParcelable("chat")!!

            viewModel.setRoom(room)
            viewModel.getMessages(room._id, CommunityViewModel.MessageType.UNREAD)
            initViews(room)
        }
    }

    private fun initViews(room: Room) {
        binding.apply {
            rvChat.adapter = adapter
            ivChat.load(room.getImage())
            tvChat.text = room.getName()
            ivBack.setOnClickListener(this@ChatFragment)
            ivSend.setOnClickListener(this@ChatFragment)
            ivChat.setOnClickListener(this@ChatFragment)
            tvChat.setOnClickListener(this@ChatFragment)
            ivMenu.setOnClickListener(this@ChatFragment)
        }
    }

    private fun checkState() {
        lifecycleScope.launch {
            viewModel.message.collect {
                it?.let { it1 ->
                    val list = ArrayList<Message>()
                    list.addAll(adapter.currentList.toList())
                    list.add(it1)
                    adapter.submitList(list)
                    binding.rvChat.smoothScrollToPosition(adapter.currentList.size)
                }
                binding.layoutMessage.editText?.text?.clear()
            }
        }
        lifecycleScope.launch {
            viewModel.state.collectLatest {
                if (it.isLoading) activity.showLoading(binding.root)
                else activity.dismissLoading(binding.root)

                if (it.error?.isNotEmpty() == true) activity.showError(
                    binding.root,
                    it.error.toString()
                )

                it.chat?.apply {
                    adapter.submitList(this.messages)
                }
            }
        }
    }

    private fun checkValidation(message: String): Boolean =
        if (message.isEmpty()) {
            binding.layoutMessage.error = getString(R.string.enter_the_message)
            false
        } else true

    override fun onClick(v: View?) {
        when (v) {
            binding.ivBack -> nav.navigateUp()
            binding.ivMenu -> {

            }

            binding.ivSend -> {
                val message = binding.layoutMessage.editText?.text.toString()
                if (checkValidation(message)) viewModel.sendMessage(message)
            }

            binding.ivChat, binding.tvChat -> {
                if (viewModel.room.value?.book?.cover?.path.isNullOrEmpty()) {
                    val bundle = Bundle()
                    bundle.putString("userId", viewModel.room.value?.partner?._id)
                    nav.navigate(R.id.action_chatFragment_to_profileFragment, bundle)
                } else {
                    viewModel.room.value?.let {
                        val bundle = Bundle()
                        bundle.putParcelable("room", it)
                        nav.navigate(R.id.action_chatFragment_to_communityDetailsFragment, bundle)
                    }
                }
            }
        }
    }
}