package com.muhmmad.qaree.ui.activity.reading_view

import android.os.Bundle
import com.muhmmad.qaree.databinding.ActivityReadingViewBinding
import com.muhmmad.qaree.ui.activity.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReadingViewActivity : BaseActivity() {
    private val binding: ActivityReadingViewBinding by lazy {
        ActivityReadingViewBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.apply {
            val bookId = intent.getStringExtra("id").toString()
        }
    }
}