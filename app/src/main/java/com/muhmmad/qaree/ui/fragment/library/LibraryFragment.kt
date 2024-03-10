package com.muhmmad.qaree.ui.fragment.library

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.muhmmad.qaree.databinding.FragmentLibraryBinding
import com.muhmmad.qaree.ui.activity.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LibraryFragment : Fragment() {
    private val binding: FragmentLibraryBinding by lazy {
        FragmentLibraryBinding.inflate(layoutInflater)
    }
    private val activity: MainActivity by lazy {
        getActivity() as MainActivity
    }
    private val viewModel: LibraryViewModel by viewModels()
    private val adapter: LibraryAdapter by lazy {
        LibraryAdapter()
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
            //checkStatus
            checkStatus()
            viewModel.getLibrary()
            rvLibrary.adapter = adapter
            btnAdd.setOnClickListener {

            }
        }
    }

    private fun checkStatus() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                if (it.isLoading) activity.showLoading()
                else activity.dismissLoading()

                if (it.error?.isNotEmpty() == true) activity.showError(it.error.toString())
                if (it.libraryResponse != null) {
                    adapter.setData(it.libraryResponse.data)
                }

                if (it.createShelfResponse != null) activity.showMessage(it.createShelfResponse.message)
            }
        }
    }
}