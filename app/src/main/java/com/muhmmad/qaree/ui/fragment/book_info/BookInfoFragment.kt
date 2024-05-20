package com.muhmmad.qaree.ui.fragment.book_info

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import coil.load
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.muhmmad.domain.model.Book
import com.muhmmad.domain.model.Room
import com.muhmmad.qaree.BuildConfig
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.DialogPaymentBinding
import com.muhmmad.qaree.databinding.FragmentBookInfoBinding
import com.muhmmad.qaree.ui.activity.home.HomeActivity
import com.muhmmad.qaree.ui.activity.reading_view.ReadingViewActivity
import com.muhmmad.qaree.ui.fragment.book_info.adapters.ReviewsAdapter
import com.muhmmad.qaree.utils.DateUtils.getBookYear
import com.paypal.android.cardpayments.ApproveOrderListener
import com.paypal.android.cardpayments.Card
import com.paypal.android.cardpayments.CardClient
import com.paypal.android.cardpayments.CardRequest
import com.paypal.android.cardpayments.CardResult
import com.paypal.android.cardpayments.threedsecure.SCA
import com.paypal.android.corepayments.Address
import com.paypal.android.corepayments.CoreConfig
import com.paypal.android.corepayments.Environment
import com.paypal.android.corepayments.PayPalSDKError
import com.paypal.android.paypalwebpayments.PayPalWebCheckoutClient
import com.paypal.android.paypalwebpayments.PayPalWebCheckoutFundingSource
import com.paypal.android.paypalwebpayments.PayPalWebCheckoutListener
import com.paypal.android.paypalwebpayments.PayPalWebCheckoutRequest
import com.paypal.android.paypalwebpayments.PayPalWebCheckoutResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
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
    private val ctx: Context by lazy {
        binding.root.context
    }
    private val viewModel: BookInfoViewModel by activityViewModels()
    private var paymentType: String = "Paypal"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val book: Book? =
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) arguments?.getParcelable(
                        "book",
                        Book::class.java
                    )
                    else arguments?.getParcelable<Book>("book") as Book
                } catch (ex: Exception) {
                    null
                }


            viewModel.updateBook(book)

            handleViews(book)
            checkState()
            viewModel.getBookStatus()
            viewModel.getReviews()
        }
    }

    private fun handleViews(book: Book?) {
        binding.apply {
            if (book != null) {
                bookInfoHeader.ivCover.load(book.cover.path)
                bookInfoHeader.tvBookName.text = book.name
                bookInfoHeader.tvBookAuthor.text =
                    getString(R.string.author_name, book.author?.name)
                bookInfoHeader.ratingBar.rating = book.avgRating.toFloat()
                bookInfoHeader.tvBookYear.text = getBookYear(book.createdAt)
                bookInfoHeader.tvBookLanguage.text = book.language
                bookInfoHeader.tvBookPrice.text =
                    getString(R.string.offer_price, book.price.toString())
                tvDescription.text = book.description
            }
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
                        showPaymentDialog()
                    }

                    BookInfoViewModel.BookState.START_READING -> {
                        val intent = Intent(context, ReadingViewActivity::class.java)
                        intent.putExtra("id", book?.id ?: "")
                        startActivity(intent)
                    }

                    BookInfoViewModel.BookState.CONTINUE_READING -> {
                        val intent = Intent(context, ReadingViewActivity::class.java)
                        intent.putExtra("id", book?.id ?: "")
                        startActivity(intent)
                    }
                }
            }

            btnList.setOnClickListener {
                nav.navigate(R.id.action_bookInfoFragment_to_bookInfoDialog)
            }
        }
    }

    private fun checkState() {
        lifecycleScope.launch {
            viewModel.paymentOrder.collectLatest {
                it?.let {
                    if (paymentType == "Paypal") paypalProcess(it.id) else cardsProcess(it.id)
                }
            }
        }
        lifecycleScope.launch {
            viewModel.bookState.collect {
                when (it) {
                    BookInfoViewModel.BookState.BUY -> binding.btnBuy.text = getString(R.string.buy)
                    BookInfoViewModel.BookState.START_READING -> binding.btnBuy.text =
                        getString(R.string.start_read)

                    BookInfoViewModel.BookState.CONTINUE_READING -> binding.btnBuy.text =
                        getString(R.string.continue_reading)
                }
            }
        }
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
//                    if (status == null) {
//
//                    } else {
//                        if (readingProgress == 0.0) binding.btnBuy.text =
//                            getString(R.string.start_read)
//                        else binding.btnBuy.text = getString(R.string.continue_reading)
//                    }
                }

                it.addBookToShelfResponse?.apply {
                    activity.showMessage(message)
                }

                it.completePaymentResponse?.apply {
                    viewModel.getBookStatus()
                }

                it.joinCommunityResponse?.apply {
//                    val bundle = Bundle()
//                    val roomId = "${viewModel.userId.value}-${user?._id}"
//                    val chat = user?.let { user ->
//                        Room(
//                            _id = roomId,
//                            roomId = roomId,
//                            partner = user,
//                        )
//                    }
//                    bundle.putParcelable("chat", chat)
//                    bundle.putString("userId", viewModel.userId.value)
//                    nav.navigate(R.id.action_bookInfoFragment_to_chatFragment, bundle)
                    activity.showMessage(message)
                }
            }
        }
    }

    private fun showPaymentDialog() {
        val dialog = BottomSheetDialog(ctx)
        val dialogBinding = DialogPaymentBinding.inflate(LayoutInflater.from(ctx))

        dialogBinding.apply {
            paypal.setOnClickListener {
                viewModel.createPaymentOrder()
                paymentType = "Paypal"
                dialog.dismiss()
            }

            cards.setOnClickListener {
                viewModel.createPaymentOrder()
                paymentType = "Cards"
                dialog.dismiss()
            }
        }

        dialog.setContentView(dialogBinding.root)
        dialog.show()
    }

    private fun paypalProcess(orderId: String) {
        val config = CoreConfig(
            BuildConfig.paypalClientId,
            environment = Environment.SANDBOX
        )


        val payPalWebCheckoutClient =
            PayPalWebCheckoutClient(activity, config, "com.muhmmad.qaree.payment")
        payPalWebCheckoutClient.listener = object : PayPalWebCheckoutListener {
            override fun onPayPalWebCanceled() {
                activity.showMessage("Canceled")
            }

            override fun onPayPalWebFailure(error: PayPalSDKError) {
                activity.showError(binding.root, error.message.toString())
            }

            override fun onPayPalWebSuccess(result: PayPalWebCheckoutResult) {
                viewModel.completePayment(result.orderId.toString())
            }
        }

        val payPalWebCheckoutRequest = PayPalWebCheckoutRequest(
            orderId,
            fundingSource = PayPalWebCheckoutFundingSource.PAYPAL
        )
        payPalWebCheckoutClient.start(payPalWebCheckoutRequest)
    }

    private fun cardsProcess(orderId: String) {
        val config = CoreConfig(
            BuildConfig.paypalClientId,
            environment = Environment.SANDBOX
        )
        val cardClient = CardClient(activity, config)

        val card = Card(
            number = "4032036725116402",
            expirationMonth = "11",
            expirationYear = "2027",
            securityCode = "867",
            billingAddress = Address(
                streetAddress = "123 Main St.",
                extendedAddress = "Apt. 1A",
                locality = "Anytown",
                region = "CA",
                postalCode = "12345",
                countryCode = "US"
            )
        )

        val cardRequest = CardRequest(
            orderId = orderId,
            card = card,
            returnUrl = "myapp://com.muhmmad.qaree.payment", // custom URL scheme needs to be configured in AndroidManifest.xml
            sca = SCA.SCA_ALWAYS // default value is SCA.SCA_WHEN_REQUIRED
        )

        cardClient.approveOrderListener = object : ApproveOrderListener {
            override fun onApproveOrderCanceled() {
                activity.showMessage("Canceled")
            }

            override fun onApproveOrderFailure(error: PayPalSDKError) {
                activity.showError(binding.root, error.message.toString())
            }

            override fun onApproveOrderSuccess(result: CardResult) {
                viewModel.completePayment(result.orderId)
            }

            override fun onApproveOrderThreeDSecureDidFinish() {
                Log.i(TAG, "Approve Order ThreeDSecure Did Finish")
            }

            override fun onApproveOrderThreeDSecureWillLaunch() {
                Log.i(TAG, "Approve Order ThreeDSecure Will Launch")
            }
        }

        cardClient.approveOrder(activity, cardRequest)
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

private const val TAG = "BookInfoFragment"