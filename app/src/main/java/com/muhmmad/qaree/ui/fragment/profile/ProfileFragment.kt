package com.muhmmad.qaree.ui.fragment.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import coil.load
import com.muhmmad.domain.model.User
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.FragmentProfileBinding
import com.muhmmad.qaree.ui.activity.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private val binding: FragmentProfileBinding by lazy {
        FragmentProfileBinding.inflate(layoutInflater)
    }
    private val nav: NavController by lazy {
        findNavController()
    }
    private val activity: HomeActivity by lazy {
        getActivity() as HomeActivity
    }
    private val adapter: ProfileLibraryAdapter by lazy {
        ProfileLibraryAdapter()
    }
    private val viewModel: ProfileViewModel by viewModels()
    private var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val userId = arguments?.getString("userId")

            checkState()
            handleViews(!userId.isNullOrEmpty())
            if (userId != null) viewModel.getAuthorInfo(userId)
            else viewModel.getProfileInfo()
            viewModel.getLibrary()
        }
    }

    private fun handleViews(isAuthor: Boolean) {
        binding.apply {
            rvLibrary.adapter = adapter
            ivSettings.isVisible = !isAuthor
            ivSettings.setOnClickListener {
                user?.let { user ->
                    val bundle = Bundle()
                    bundle.putParcelable("user", user)
                    nav.navigate(R.id.action_profileFragment_to_settingsFragment, bundle)
                }
            }
            ivBack.setOnClickListener {
                nav.navigateUp()
            }
        }
    }

    private fun checkState() {
        lifecycleScope.launch {
            viewModel.state.collect {
                if (it.isLoading) activity.showLoading(binding.root) else activity.dismissLoading(
                    binding.root
                )
                if (it.error?.isNotEmpty() == true) activity.showError(
                    binding.root,
                    it.error.toString()
                )
                it.userDataResponse?.apply {
                    user = this
                    if (avatar?.path != "") binding.ivUser.load(avatar?.path) {
                        placeholder(R.drawable.ic_profile_avatar)
                    }
                    binding.tvUserName.text = name
                    binding.tvBio.text = bio
                }

                it.libraryResponse?.apply {
                    adapter.setData(data)
                }
            }
        }
    }
}