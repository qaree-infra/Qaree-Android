package com.muhmmad.qaree.ui.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.transform.CircleCropTransformation
import com.muhmmad.qaree.databinding.FragmentHomeBinding
import com.muhmmad.qaree.ui.activity.main.MainActivity
import com.muhmmad.qaree.ui.fragment.home.adapters.AuthorsAdapter
import com.muhmmad.qaree.ui.fragment.home.adapters.BooksAdapter
import com.muhmmad.qaree.ui.fragment.home.adapters.CategoriesAdapter
import com.muhmmad.qaree.ui.fragment.home.adapters.OffersAdapter
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private const val TAG = "HomeFragment"

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }
    private val activity: MainActivity by lazy {
        getActivity() as MainActivity
    }
    private val offersAdapter: OffersAdapter by lazy {
        OffersAdapter()
    }
    private val authorsAdapter: AuthorsAdapter by lazy {
        AuthorsAdapter()
    }
    private val categoriesAdapter: CategoriesAdapter by lazy {
        CategoriesAdapter()
    }
    private val newReleaseAdapter: BooksAdapter by lazy {
        BooksAdapter()
    }
    private val bestSellerAdapter: BooksAdapter by lazy {
        BooksAdapter()
    }
    private val viewModel: HomeViewModel by viewModels()

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
            initViews()
            initAnimation()
            getData()
        }
    }

    private fun initViews() {
        binding.apply {
            rvOffers.adapter = offersAdapter
            rvAuthors.adapter = authorsAdapter
            rvCategories.adapter = categoriesAdapter
            rvNewReleases.adapter = newReleaseAdapter
            rvBestSellers.adapter = bestSellerAdapter
        }
    }

    private fun getData() {
        viewModel.getOffers()
    }

    private fun checkState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                if (it.isLoading) initAnimation()
                else dismissAnimation()

                if (it.error?.isNotEmpty() == true) activity.showError(it.error.toString())
                else {
                    it.activitiesResponse?.apply {
                        binding.tvActivitiesName.text = bookName
                        binding.tvReadingPages.text = "$userPages/$bookPages"
                        binding.progressBar.progress = percentage
                        binding.tvProgressPresent.text = "$percentage%"
                        binding.ivActivitiesBook.load(image) {
                            transformations(CircleCropTransformation())
                        }
                    }

                    it.offersResponse?.apply {
                        offersAdapter.setData(data)
                    }

                    it.authorsResponse?.apply {
                        authorsAdapter.setData(data)
                    }

                    it.categoriesResponse?.apply {
                        categoriesAdapter.setData(data)
                    }

                    it.newReleasesResponse?.apply {
                        newReleaseAdapter.setData(data)
                    }

                    it.bestSellerResponse?.apply {
                        bestSellerAdapter.setData(data)
                    }
                }
            }
        }
    }

    private fun initAnimation() {
        binding.apply {
            shimmerLayout.startShimmerAnimation()
        }
    }

    private fun dismissAnimation() {
        binding.apply {
            shimmerLayout.stopShimmerAnimation()
        }
    }
}