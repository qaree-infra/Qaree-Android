package com.muhmmad.qaree.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.muhmmad.qaree.databinding.DialogEditAvatarBinding
import com.muhmmad.qaree.viewModel.EditProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditAvatarDialog : DialogFragment() {
    private val binding: DialogEditAvatarBinding by lazy {
        DialogEditAvatarBinding.inflate(layoutInflater)
    }
    private val viewModel: EditProfileViewModel by activityViewModels()

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
            tvCamera.setOnClickListener {
                viewModel.setUpdateAvatarType(EditProfileViewModel.UpdateImagesType.CAMERA)
                this@EditAvatarDialog.dismiss()
            }
            tvGallery.setOnClickListener {
                viewModel.setUpdateAvatarType(EditProfileViewModel.UpdateImagesType.GALLERY)
                this@EditAvatarDialog.dismiss()
            }
        }
    }
}