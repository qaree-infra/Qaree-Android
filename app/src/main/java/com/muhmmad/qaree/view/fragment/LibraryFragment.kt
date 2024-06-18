package com.muhmmad.qaree.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.FragmentLibraryBinding
import com.muhmmad.qaree.view.activity.HomeActivity
import com.muhmmad.qaree.view.adapters.LibraryAdapter
import com.muhmmad.qaree.view.dialog.AddShelfDialog
import com.muhmmad.qaree.viewModel.LibraryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LibraryFragment : Fragment() {
    private val binding: FragmentLibraryBinding by lazy {
        FragmentLibraryBinding.inflate(layoutInflater)
    }
    private val activity: HomeActivity by lazy {
        getActivity() as HomeActivity
    }
    private val nav: NavController by lazy {
        findNavController()
    }
    private val viewModel: LibraryViewModel by activityViewModels()
    private val adapter: LibraryAdapter by lazy {
        LibraryAdapter {
            val bundle = Bundle()
            bundle.putString("id", it)
            nav.navigate(R.id.action_libraryFragment_to_shelfFragment, bundle)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            //checkStatus
            checkStatus()
            viewModel.getLibrary()
            rvLibrary.adapter = adapter
            btnAdd.setOnClickListener {
                AddShelfDialog().show(parentFragmentManager, "Add Shelf Dialog")
            }
        }
    }

    private fun checkStatus() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                if (it.isLoading) activity.showLoading(binding.root)
                else activity.dismissLoading(binding.root)

                if (it.error?.isNotEmpty() == true) activity.showError(
                    binding.root,
                    it.error.toString()
                )
                if (it.libraryResponse != null) {
                    adapter.setData(it.libraryResponse.data)
                }

                if (it.createShelfResponse != null) {
                    activity.showMessage(it.createShelfResponse.message)
                    viewModel.getLibrary()
                }
            }
        }
    }
}