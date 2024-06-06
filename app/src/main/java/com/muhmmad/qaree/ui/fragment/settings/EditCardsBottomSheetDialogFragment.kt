package com.muhmmad.qaree.ui.fragment.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.EditCardsBottomSheetDialogBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditCardsBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private val binding: EditCardsBottomSheetDialogBinding by lazy {
        EditCardsBottomSheetDialogBinding.inflate(layoutInflater)
    }

    private val adapter: EditCardsAdapter by lazy {
        EditCardsAdapter {
            viewModel.deleteCard(it)
        }
    }

    private val viewModel: EditPaymentCardsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleScope.launch {
                viewModel.cards.collectLatest {
                    it?.let {
                        adapter.setData(it)
                    }
                }
            }

            rvEditCards.adapter = adapter
            llAddCard.setOnClickListener {
                findNavController().navigate(R.id.action_editCardsBottomSheetDialogFragment_to_addPaymentCardFragment)
            }
            tvCancel.setOnClickListener {
                this@EditCardsBottomSheetDialogFragment.dismiss()
            }
        }
    }
}