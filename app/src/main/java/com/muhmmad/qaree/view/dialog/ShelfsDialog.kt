package com.muhmmad.qaree.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.muhmmad.qaree.databinding.DialogShelfsBinding
import com.muhmmad.qaree.view.adapters.DialogAddToShelfAdapter
import com.muhmmad.qaree.viewModel.BookInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShelfsDialog : DialogFragment() {
    private val binding: DialogShelfsBinding by lazy {
        DialogShelfsBinding.inflate(layoutInflater)
    }
    private val adapter: DialogAddToShelfAdapter by lazy {
        DialogAddToShelfAdapter {
            viewModel.addBookToShelf(it)
            this@ShelfsDialog.dismiss()
        }
    }
    private val viewModel: BookInfoViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            rvShelfs.adapter = adapter
            checkState()
        }
    }

    private fun checkState() {
        lifecycleScope.launch {
            viewModel.libraryResponse.collectLatest {
                it?.let {
                    adapter.setData(it.data)
                }
            }
        }
    }
}