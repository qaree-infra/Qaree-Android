package com.muhmmad.qaree.ui.fragment.inbox

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.FragmentInboxBinding
import com.muhmmad.qaree.ui.activity.home.HomeActivity
import com.muhmmad.qaree.ui.fragment.CommunityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InboxFragment : Fragment() {
    private val binding: FragmentInboxBinding by lazy {
        FragmentInboxBinding.inflate(layoutInflater)
    }
    private val activity: HomeActivity by lazy {
        getActivity() as HomeActivity
    }
    private val adapter: InboxAdapter by lazy {
        InboxAdapter {
            val bundle = Bundle()
            bundle.putParcelable("chat", it)
            findNavController().navigate(R.id.action_inboxFragment_to_chatFragment, bundle)
        }
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
            rvInbox.adapter = adapter
            layoutSearch.editText?.doOnTextChanged { text, start, before, count ->
                viewModel.getRooms(text.toString())
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

                it.chats?.apply {
                    adapter.setData(this)
                }
            }
        }
    }
}

private const val TAG = "InboxFragment"