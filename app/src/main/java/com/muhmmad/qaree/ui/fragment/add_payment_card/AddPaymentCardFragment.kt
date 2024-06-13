package com.muhmmad.qaree.ui.fragment.add_payment_card

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.muhmmad.domain.model.Card
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.FragmentAddPaymentCardBinding
import com.muhmmad.qaree.ui.activity.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Calendar

@AndroidEntryPoint
class AddPaymentCardFragment : Fragment() {
    private val binding: FragmentAddPaymentCardBinding by lazy {
        FragmentAddPaymentCardBinding.inflate(layoutInflater)
    }
    private val activity: HomeActivity by lazy {
        getActivity() as HomeActivity
    }
    private val viewModel: AddPaymentCardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleViews()
        lifecycleScope.launch {
            viewModel.expiryDate.collectLatest {
                binding.paymentCard.tvExpiryDate.text = it
            }
        }
    }

    private fun handleViews() {
        binding.apply {
            val months = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
            val monthsAdapter =
                ArrayAdapter(root.context, android.R.layout.simple_spinner_item, months)
            tvExpiryMonth.setAdapter(monthsAdapter)

            val currentYear = Calendar.getInstance().get(Calendar.YEAR)
            val years = ArrayList<Int>()
            for (year in currentYear..currentYear + 10) {
                years.add(year)
            }
            val yearsAdapter =
                ArrayAdapter(root.context, android.R.layout.simple_spinner_item, years)
            tvExpiryYear.setAdapter(yearsAdapter)

            layoutName.editText?.doOnTextChanged { text, start, before, count ->
                paymentCard.tvCardHolderName.text = text.toString()
            }

            layoutCardNumber.editText?.doOnTextChanged { text, start, before, count ->
                paymentCard.tvCardNumber.text = text.toString()
            }

            tvExpiryMonth.doOnTextChanged { text, start, before, count ->
                viewModel.expiryMonth.value = text.toString()
            }

            tvExpiryYear.doOnTextChanged { text, start, before, count ->
                viewModel.expiryYear.value = text.toString()
            }

            btnAdd.setOnClickListener {
                val name = layoutName.editText?.text.toString()
                val cardNumber = layoutCardNumber.editText?.text.toString()
                val expiryMonth = tvExpiryMonth.text.toString()
                val expiryYear = tvExpiryYear.text.toString()
                val expireData = LocalDate.of(expiryYear.toInt(), expiryMonth.toInt(), 1)
                val cvv = layoutCvv.editText?.text.toString()
                if (checkValidation(name, cardNumber, expireData, cvv)) {
                    viewModel.addPaymentCard(
                        Card(
                            name = name,
                            number = cardNumber,
                            cvv = cvv,
                            expireMonth = expiryMonth,
                            expireYear = expiryYear,
                            image = 1
                        )
                    )
                    findNavController().navigateUp()
                }
            }
            ivBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun checkValidation(
        name: String,
        cardNumber: String,
        expireData: LocalDate,
        cvv: String
    ): Boolean {
        if (name.isEmpty()) {
            binding.layoutName.error = getString(R.string.enter_card_holder_name)
            return false
        } else if (name.length < 6) {
            binding.layoutName.error = getString(R.string.enter_valid_card_holder_name)
            return false
        } else binding.layoutName.error = null

        if (cardNumber.isEmpty()) {
            binding.layoutCardNumber.error = getString(R.string.enter_card_number)
            return false
        } else if (cardNumber.length != 16) {
            binding.layoutCardNumber.error = getString(R.string.enter_valid_card_number)
            return false
        } else binding.layoutCardNumber.error = null

        val currentDate = LocalDate.now().plusMonths(1)
        if (expireData.isBefore(currentDate)) {
            activity.showError(binding.root, getString(R.string.your_card_is_expired))
            return false
        }

        if (cvv.isEmpty()) {
            binding.layoutCvv.error = getString(R.string.enter_cvv)
            return false
        } else if (cvv.length != 3) {
            binding.layoutCvv.error = getString(R.string.enter_valid_cvv)
            return false
        } else binding.layoutCvv.error = null
        return true
    }
}