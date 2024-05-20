package com.muhmmad.qaree.ui.fragment.book_info.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.muhmmad.qaree.databinding.DialogShelfsBinding
import com.muhmmad.qaree.ui.fragment.book_info.BookInfoViewModel
import com.muhmmad.qaree.ui.fragment.book_info.adapters.ShelfsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShelfsDialog : DialogFragment() {
    private val binding: DialogShelfsBinding by lazy {
        DialogShelfsBinding.inflate(layoutInflater)
    }
    private val adapter: ShelfsAdapter by lazy {
        ShelfsAdapter {
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
            rvShelfs.adapter=adapter
            checkState()
        }
    }

    private fun checkState() {
        lifecycleScope.launch {
            viewModel.state.collect {
                it.libraryResponse?.apply {
                    adapter.setData(data)
                }
            }
        }
    }
}