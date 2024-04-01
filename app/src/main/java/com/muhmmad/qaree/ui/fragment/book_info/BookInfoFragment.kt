package com.muhmmad.qaree.ui.fragment.book_info

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import coil.load
import com.muhmmad.domain.model.Book
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.FragmentBookInfoBinding
import com.muhmmad.qaree.ui.activity.home.HomeActivity
import com.muhmmad.qaree.ui.activity.reading_view.ReadingViewActivity
import com.muhmmad.qaree.ui.fragment.book_info.adapters.ReviewsAdapter
import com.muhmmad.qaree.utils.DateUtils.getBookYear
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
    private val viewModel: BookInfoViewModel by activityViewModels()
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

            viewModel.updateBook(book)

            handleViews(book)
            checkState(book)
            viewModel.getBookStatus()
            viewModel.getReviews()
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
            rvReviews.adapter = adapter
            ivBack.setOnClickListener {
                nav.navigateUp()
            }
            tvPost.setOnClickListener {
                val content = edtComment.text.toString()
                val rate = userRate.rating
                if (checkValidation(content, rate)) {
                    viewModel.makeReview(rate, content)
                }
            }
            btnBuy.setOnClickListener {
                when (viewModel.bookState.value) {
                    BookInfoViewModel.BookState.BUY -> {

                    }

                    BookInfoViewModel.BookState.START_READING -> {
                        val intent = Intent(context, ReadingViewActivity::class.java)
                        intent.putExtra("id", book.id)
                        startActivity(intent)
                    }

                    BookInfoViewModel.BookState.CONTINUE_READING -> {

                    }
                }
            }

            btnList.setOnClickListener {
                nav.navigate(R.id.action_bookInfoFragment_to_bookInfoDialog)
            }
        }
    }

    private fun checkState(book: Book) {
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

                it.bookStatus?.apply {
                    if (status == "purchased" || book.price == 0.0) {
                        if (readingProgress == 0.0) binding.btnBuy.text =
                            getString(R.string.start_read)
                        else binding.btnBuy.text = getString(R.string.continue_reading)
                    } else binding.btnBuy.text = getString(R.string.buy)
                }

                it.addBookToShelfResponse?.apply {
                    activity.showMessage(message)
                }
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