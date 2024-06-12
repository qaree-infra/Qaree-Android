package com.muhmmad.qaree.ui.fragment.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.FragmentSearchBinding
import com.muhmmad.qaree.ui.activity.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private val binding: FragmentSearchBinding by lazy {
        FragmentSearchBinding.inflate(layoutInflater)
    }
    private val activity: HomeActivity by lazy {
        getActivity() as HomeActivity
    }
    private val nav: NavController by lazy {
        findNavController()
    }
    private val categoriesAdapter: CategoriesAdapter by lazy {
        CategoriesAdapter {
            val bundle = Bundle()
            bundle.putString("id", it)
            nav.navigate(R.id.action_searchFragment_to_categoryFragment, bundle)
        }
    }
    private val searchAdapter: SearchAdapter by lazy {
        SearchAdapter {
            val bundle = Bundle()
            bundle.putParcelable("book", it)
            nav.navigate(R.id.action_searchFragment_to_bookInfoFragment, bundle)
        }
    }
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            checkState()
            viewModel.getCategories()
            rvCategories.adapter = categoriesAdapter
            rvResult.adapter = searchAdapter
            layoutSearch.editText?.addTextChangedListener(afterTextChanged = {
                if (it.toString().isNotEmpty()) viewModel.search(it.toString())
            })
            ivBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun checkState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                if (it.isLoading) activity.showLoading(binding.root)
                else activity.dismissLoading(binding.root)

                if (it.error?.isNotEmpty() == true) activity.showError(
                    binding.root,
                    it.error.toString()
                )

                if (it.categoriesResponse != null) {
                    categoriesAdapter.setData(it.categoriesResponse.data)
                }
                if (it.searchResponse != null) {
                    if (binding.rvCategories.visibility == View.VISIBLE) {
                        binding.tvBrowse.visibility = View.GONE
                        binding.rvCategories.visibility = View.GONE
                        binding.rvResult.visibility = View.VISIBLE
                    }
                    searchAdapter.setData(it.searchResponse.data)
                }
            }
        }
    }
}