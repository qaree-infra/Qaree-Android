package com.muhmmad.qaree.ui.fragment.book_info.dialogs

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.DialogBookInfoBinding
import com.muhmmad.qaree.ui.activity.reading_view.ReadingViewActivity
import com.muhmmad.qaree.ui.fragment.book_info.BookInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

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
            tvCancel.setOnClickListener {
                this@BookInfoDialog.dismiss()
            }
            tvShelf.setOnClickListener {
                nav.navigate(R.id.shelfsDialog)
            }
            tvSample.setOnClickListener {
                val intent = Intent(context, ReadingViewActivity::class.java)
                intent.putExtra("id", viewModel.book.value.id)
                startActivity(intent)
            }
        }
    }
}