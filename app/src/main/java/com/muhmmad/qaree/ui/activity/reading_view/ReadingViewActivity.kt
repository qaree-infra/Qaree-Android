package com.muhmmad.qaree.ui.activity.reading_view

import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.muhmmad.qaree.databinding.ActivityReadingViewBinding
import com.muhmmad.qaree.ui.activity.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "ReadingViewActivity"

@AndroidEntryPoint
class ReadingViewActivity : BaseActivity() {
    private val binding: ActivityReadingViewBinding by lazy {
        ActivityReadingViewBinding.inflate(layoutInflater)
    }
    private val bookId: String by lazy {
        intent.getStringExtra("id").toString()
    }
    private val adapter: ReadingViewAdapter by lazy {
        ReadingViewAdapter()
    }
    private val viewModel: ReadingViewViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.apply {
            checkState()
            binding.recyclerView.adapter = adapter
            viewModel.getBookContent(bookId)
        }
    }

    private fun checkState() {
        lifecycleScope.launch {
            viewModel.bookContent.collectLatest {
                viewModel.token.value?.let { token ->
                    viewModel.getChapter(token, bookId, it?.data ?: emptyList()).collectLatest {
                        adapter.submitData(it)
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewModel.state.collect {
                it.error?.apply {
                    this@ReadingViewActivity.showError(binding.root, this)
                }

                if (it.isLoading) this@ReadingViewActivity.showLoading(binding.root) else this@ReadingViewActivity.dismissLoading(
                    binding.root
                )
            }
        }
    }
}