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
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.FragmentHomeBinding
import com.muhmmad.qaree.ui.activity.home.HomeActivity
import com.muhmmad.qaree.ui.fragment.home.adapters.AuthorsAdapter
import com.muhmmad.qaree.ui.fragment.home.adapters.BooksAdapter
import com.muhmmad.qaree.ui.fragment.home.adapters.CategoriesAdapter
import com.muhmmad.qaree.ui.fragment.home.adapters.OffersAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }
    private val activity: HomeActivity by lazy {
        getActivity() as HomeActivity
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
        viewModel.apply {
            getOffers()
            getLastActivity()
            getTopAuthors()
            getNewReleaseBooks()
            getCategories()
            getBestSellerBooks()
        }
    }

    private fun checkState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                if (it.isLoading) initAnimation()
                else dismissAnimation()

                if (it.error?.isNotEmpty() == true) activity.showError(
                    binding.root,
                    it.error.toString()
                )
                else {
                    if (it.activitiesResponse != null) {
                        binding.tvActivities.visibility = View.VISIBLE
                        binding.activities.visibility = View.VISIBLE
                        it.activitiesResponse.apply {
                            binding.tvActivitiesName.text = book.name
//                            binding.tvReadingPages.text = "$readingProgress/100"
                            binding.progressBar.progress = readingProgress
                            binding.tvProgressPresent.text =
                                getString(R.string.reading_percent, readingProgress.toString())
                            binding.ivActivitiesBook.load(book.cover.path) {
                                transformations(CircleCropTransformation())
                            }
                        }
                    } else {
                        binding.tvActivities.visibility = View.GONE
                        binding.activities.visibility = View.GONE
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
            if (!isFirstTime) {
                isFirstTime = true
                shimmerLayout.startShimmerAnimation()
            }
        }
    }

    private fun dismissAnimation() {
        binding.apply {
            shimmerLayout.stopShimmerAnimation()
        }
    }

    companion object {
        var isFirstTime: Boolean = false
    }
}
