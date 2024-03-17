package com.muhmmad.qaree.ui.fragment.book_info

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import coil.load
import com.muhmmad.domain.model.Book
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.FragmentBookInfoBinding
import com.muhmmad.qaree.ui.activity.home.HomeActivity
import com.muhmmad.qaree.utils.DateUtils.getBookYear
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private const val TAG = "BookInfoFragment"

@AndroidEntryPoint
class BookInfoFragment : Fragment() {
    private val binding: FragmentBookInfoBinding by lazy {
        FragmentBookInfoBinding.inflate(layoutInflater)
    }
    private val nav: NavController by lazy {
        findNavController()
    }
    private val activity: HomeActivity by lazy {
        getActivity() as HomeActivity
    }
    private val adapter: ReviewsAdapter by lazy {
        ReviewsAdapter()
    }
    private val viewModel: BookInfoViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val book: Book =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) arguments?.getSerializable(
                    "book",
                    Book::class.java
                )!!
                else arguments?.getSerializable("book") as Book

            handleViews(book)
            checkState()
            viewModel.getReviews(book.id)
        }
    }

    private fun handleViews(book: Book) {
        binding.apply {
            bookInfoHeader.ivCover.load(book.cover.path)
            bookInfoHeader.tvBookName.text = book.name
            bookInfoHeader.tvBookAuthor.text = getString(R.string.author_name, book.author?.name)
            bookInfoHeader.ratingBar.rating = book.avgRating.toFloat()
            bookInfoHeader.tvBookYear.text = getBookYear(book.createdAt)
            bookInfoHeader.tvBookLanguage.text = book.language
            bookInfoHeader.tvBookPrice.text = getString(R.string.offer_price, book.price.toString())
            tvDescription.text = book.description
            rvReviews.adapter=adapter
            ivBack.setOnClickListener {
                nav.navigateUp()
            }
            tvPost.setOnClickListener {
                val content = edtComment.text.toString()
                val rate = userRate.rating
                if (checkValidation(content, rate)) {
                    viewModel.makeReview(book.id, rate, content)
                }
            }
        }
    }

    private fun checkState() {
        lifecycleScope.launch {
            viewModel.state.collect {
                if (it.isLoading) activity.showLoading(binding.root) else activity.dismissLoading(
                    binding.root
                )

                if (it.error?.isNotEmpty() == true) activity.showError(
                    binding.root,
                    it.error.toString()
                )

                if (it.reviewsResponse != null) {
                    binding.bookInfoHeader.tvRatingNumber.text =
                        getString(R.string.ratings, it.reviewsResponse.total.toString())
                    adapter.setData(it.reviewsResponse.data)
                }

                if (it.makeReviewResponse != null) activity.showMessage(it.makeReviewResponse.message)
            }
        }
    }

    private fun checkValidation(content: String, rate: Float): Boolean {
        if (content.isEmpty()) {
            binding.edtComment.error = getString(R.string.please_enter_your_comment)
            return false
        } else if (content.length < 3) {
            binding.edtComment.error = getString(R.string.please_enter_valid_comment)
            return false
        } else {
            binding.edtComment.error = null
        }

        if (rate == 0.0F) {
            activity.showError(binding.root, getString(R.string.please_enter_your_rate))
            return false
        }
        return true
    }
}