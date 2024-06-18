package com.muhmmad.qaree.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.AddShelfDialogBinding
import com.muhmmad.qaree.viewModel.LibraryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddShelfDialog : DialogFragment() {
    private val binding: AddShelfDialogBinding by lazy {
        AddShelfDialogBinding.inflate(layoutInflater)
    }
    private val viewModel: LibraryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnSave.setOnClickListener {
                val name = layoutName.editText?.text.toString()
                if (checkValidation(name)) {
                    viewModel.createShelf(name)
                    this@AddShelfDialog.dismiss()
                }
            }
            btnCancel.setOnClickListener {
                this@AddShelfDialog.dismiss()
            }
        }
    }

    private fun checkValidation(name: String): Boolean {
        if (name.isEmpty()) {
            binding.layoutName.error = getString(R.string.please_enter_shelf_name)
            return false
        } else if (name.length < 5) {
            binding.layoutName.error = getString(R.string.shelf_name_should_be_more_than_5_chars)
            return false
        }
        return true
    }
}