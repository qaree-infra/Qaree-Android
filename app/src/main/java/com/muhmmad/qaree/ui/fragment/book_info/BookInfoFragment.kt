package com.muhmmad.qaree.ui.fragment.book_info

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.muhmmad.qaree.databinding.FragmentBookInfoBinding
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "BookInfoFragment"

@AndroidEntryPoint
class BookInfoFragment : Fragment() {
    private val binding: FragmentBookInfoBinding by lazy {
        FragmentBookInfoBinding.inflate(layoutInflater)
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
            val bookId = arguments?.getString("id").toString()

            userRate.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
                Log.i(TAG, "Changing")
            }
        }
    }
}