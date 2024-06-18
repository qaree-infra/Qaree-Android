package com.muhmmad.qaree.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.CardsBottomSheetDialogBinding
import com.muhmmad.qaree.view.activity.HomeActivity
import com.muhmmad.qaree.view.adapters.CardsAdapter
import com.muhmmad.qaree.viewModel.BookInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CardsBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private val binding: CardsBottomSheetDialogBinding by lazy {
        CardsBottomSheetDialogBinding.inflate(layoutInflater)
    }

    private val viewModel: BookInfoViewModel by activityViewModels()

    private val adapter: CardsAdapter by lazy {
        CardsAdapter()
    }

    private val activity: HomeActivity by lazy {
        getActivity() as HomeActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            checkState()
            viewModel.getPaymentCards()
            rvCards.adapter = adapter
            llAddCard.setOnClickListener {
                findNavController().navigate(R.id.action_cardsBottomSheetDialogFragment_to_addPaymentCardFragment)
            }
            tvCancel.setOnClickListener {
                this@CardsBottomSheetDialogFragment.dismiss()
            }
            btnSubmit.setOnClickListener {
                if (adapter.getCheckedItem() == null) {
                    activity.showError(binding.root, getString(R.string.choose_payment_card))
                    return@setOnClickListener
                }
                viewModel.setPaymentCard(adapter.getCheckedItem()!!)
                this@CardsBottomSheetDialogFragment.dismiss()
            }
        }
    }

    private fun checkState() {
        lifecycleScope.launch {
            viewModel.paymentCards.collectLatest {
                if (it.isNullOrEmpty()) {
                    binding.tvNoCards.visibility = View.VISIBLE
                    binding.rvCards.visibility = View.GONE
                } else {
                    adapter.setData(it)
                    binding.tvNoCards.visibility = View.GONE
                    binding.rvCards.visibility = View.VISIBLE
                }
            }
        }
    }
}