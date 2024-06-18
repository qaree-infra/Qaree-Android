package com.muhmmad.qaree.view.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.map
import com.muhmmad.domain.model.NetworkResponse
import com.muhmmad.qaree.databinding.ActivityReadingViewBinding
import com.muhmmad.qaree.view.adapters.ReadingViewAdapter
import com.muhmmad.qaree.viewModel.ReadingViewViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
            viewModel.state.collectLatest {
                it.error?.apply { showError(binding.root, this) }
                if (it.isLoading) showLoading(binding.root) else dismissLoading(binding.root)

                it.chapter?.let {
                    adapter.submitData(it)
                    it.map {
                        if (it is NetworkResponse.Error) {
                            showError(binding.root, it.message.toString())
                        }
                    }
                }
            }
        }
    }
}