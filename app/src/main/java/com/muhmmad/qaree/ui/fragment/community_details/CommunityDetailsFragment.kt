package com.muhmmad.qaree.ui.fragment.community_details

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import coil.load
import com.muhmmad.domain.model.Room
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.FragmentCommunityDetailsBinding
import com.muhmmad.qaree.ui.activity.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "CommunityDetailsFragmen"

@AndroidEntryPoint
class CommunityDetailsFragment : Fragment() {
    private val binding: FragmentCommunityDetailsBinding by lazy {
        FragmentCommunityDetailsBinding.inflate(layoutInflater)
    }
    private val nav: NavController by lazy {
        findNavController()
    }
    private val activity: HomeActivity by lazy {
        getActivity() as HomeActivity
    }
    private val viewModel: CommunityDetailsViewModel by viewModels()
    private val adapter: MembersAdapter by lazy {
        MembersAdapter {
            val bundle = Bundle()
            bundle.putString("userId", it._id)
            nav.navigate(R.id.action_communityDetailsFragment_to_profileFragment, bundle)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val room: Room =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) arguments?.getParcelable(
                    "room",
                    Room::class.java
                )!!
                else arguments?.getParcelable("room")!!

            viewModel.getCommunityMembers(room.roomId)

            checkState()
            handleViews(room)
        }
    }

    private fun checkState() {
        lifecycleScope.launch {
            viewModel.communityMembers.collectLatest {
                it?.let {
                    binding.tvCommunityMembers.text =
                        getString(R.string.members, it.totalMembers.toString())
                    adapter.setData(it.members)
                }
            }
        }
        lifecycleScope.launch {
            viewModel.state.collectLatest {
                if (it.isLoading) activity.showLoading(binding.root)
                else activity.dismissLoading(binding.root)

                if (it.error?.isNotBlank() == true) activity.showError(
                    binding.root,
                    it.error.toString()
                )
            }
        }
    }

    private fun handleViews(room: Room) {
        binding.apply {
            ivBack.setOnClickListener {
                nav.navigateUp()
            }
            ivCommunity.load(room.book.cover.path)
            tvCommunity.text = room.book.name
            rvMembers.adapter = adapter
        }
    }
}