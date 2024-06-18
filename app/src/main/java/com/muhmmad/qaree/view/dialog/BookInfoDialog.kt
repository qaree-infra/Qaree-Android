package com.muhmmad.qaree.view.dialog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.DialogBookInfoBinding
import com.muhmmad.qaree.view.activity.ReadingViewActivity
import com.muhmmad.qaree.viewModel.BookInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookInfoDialog : DialogFragment() {
    private val binding: DialogBookInfoBinding by lazy {
        DialogBookInfoBinding.inflate(layoutInflater)
    }
    private val nav: NavController by lazy {
        findNavController()
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
            checkState()
            tvCancel.setOnClickListener {
                this@BookInfoDialog.dismiss()
            }
            tvShelf.setOnClickListener {
                nav.navigate(R.id.action_bookInfoDialog_to_shelfsDialog)
            }
            tvSample.setOnClickListener {
                val intent = Intent(context, ReadingViewActivity::class.java)
                intent.putExtra("id", viewModel.book.value.id)
                startActivity(intent)
            }
            tvCommunity.setOnClickListener {
                viewModel.joinCommunity()
            }
        }
    }

    private fun checkState() {
        lifecycleScope.launch {
            viewModel.bookState.collect {
                when (it) {
                    BookInfoViewModel.BookState.BUY -> {
                        binding.llSample.visibility = View.VISIBLE
                        binding.llCommunity.visibility = View.GONE
                    }

                    else -> {
                        binding.llSample.visibility = View.GONE
                        binding.llCommunity.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}