package com.muhmmad.qaree.ui.fragment.shelf

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.FragmentShelfBinding
import com.muhmmad.qaree.ui.activity.home.HomeActivity
import com.muhmmad.qaree.ui.activity.reading_view.ReadingViewActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShelfFragment : Fragment() {
    private val binding: FragmentShelfBinding by lazy {
        FragmentShelfBinding.inflate(layoutInflater)
    }
    private val activity: HomeActivity by lazy {
        getActivity() as HomeActivity
    }
    private val nav: NavController by lazy {
        findNavController()
    }
    private val ctx: Context by lazy {
        binding.root.context
    }
    private var shelfId: String = ""
    private val adapter: ShelfAdapter by lazy {
        ShelfAdapter(onReadClick = {
            val intent = Intent(ctx, ReadingViewActivity::class.java)
            intent.putExtra("id", it)
            startActivity(intent)
        }, onBookInfoClick = {
            val bundle = Bundle()
            bundle.putString("id", it)
            nav.navigate(R.id.action_shelfFragment_to_bookInfoFragment, bundle)
        }, onRemoveClick = {
            viewModel.removeBookFromShelf(bookId = it, shelfId = shelfId)
        })
    }
    private val viewModel: ShelfViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            shelfId = arguments?.getString("id").toString()
            viewModel.getShelfDetails(shelfId)
            checkState()
            rvBooks.adapter = adapter
        }
    }

    private fun checkState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                if (it.isLoading) activity.showLoading(binding.root) else activity.dismissLoading(
                    binding.root
                )

                if (it.error?.isNotEmpty() == true) activity.showError(
                    binding.root,
                    it.error.toString()
                )

                if (it.shelfResponse != null) {
                    binding.tvShelf.text = it.shelfResponse.name
                    adapter.setData(it.shelfResponse.books)
                }

                if (it.removeBook != null) {
                    if (it.removeBook.success) viewModel.getShelfDetails(shelfId)
                    activity.showMessage(it.removeBook.message)
                }
            }
        }
    }
}