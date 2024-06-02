package com.muhmmad.qaree.ui.fragment.chat.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.muhmmad.qaree.databinding.DeleteChatDialogBinding
import com.muhmmad.qaree.databinding.LeaveGroupDialogBinding
import com.muhmmad.qaree.ui.fragment.CommunityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteChatDialog : DialogFragment() {
    private val binding: DeleteChatDialogBinding by lazy {
        DeleteChatDialogBinding.inflate(layoutInflater)
    }
    private val viewModel: CommunityViewModel by activityViewModels()

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
            tvDelete.setOnClickListener {
                viewModel.deleteChat()
            }

            btnCancel.setOnClickListener {
                this@DeleteChatDialog.dismiss()
            }
        }
    }
}