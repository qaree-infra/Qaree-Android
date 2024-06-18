package com.muhmmad.qaree.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.FragmentCategoryBinding
import com.muhmmad.qaree.view.activity.HomeActivity
import com.muhmmad.qaree.view.adapters.BooksAdapter
import com.muhmmad.qaree.viewModel.CategoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryFragment : Fragment() {
    private val binding: FragmentCategoryBinding by lazy {
        FragmentCategoryBinding.inflate(layoutInflater)
    }
    private val activity: HomeActivity by lazy {
        getActivity() as HomeActivity
    }
    private val nav: NavController by lazy {
        findNavController()
    }
    private val adapter: BooksAdapter by lazy {
        BooksAdapter {
            val bundle = Bundle()
            bundle.putParcelable("book", it)
            nav.navigate(R.id.action_categoryFragment_to_bookInfoFragment, bundle)
        }
    }
    private val viewModel: CategoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val categoryId = arguments?.getString("id").toString()
            viewModel.getBooksByCategory(categoryId)
            checkState()
            rvBooks.adapter = adapter
            ivBack.setOnClickListener {
                nav.navigateUp()
            }
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

                if (it.response != null) {
                    adapter.setData(it.response.data)
                }
            }
        }
    }
}