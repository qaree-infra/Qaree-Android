package com.muhmmad.qaree.ui.fragment.edit_profile

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import coil.load
import com.muhmmad.domain.model.User
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.FragmentEditProfileBinding
import com.muhmmad.qaree.ui.activity.home.HomeActivity
import kotlinx.coroutines.launch

class EditProfileFragment : Fragment() {
    private val binding: FragmentEditProfileBinding by lazy {
        FragmentEditProfileBinding.inflate(layoutInflater)
    }
    private val nav: NavController by lazy {
        findNavController()
    }
    private val activity: HomeActivity by lazy {
        getActivity() as HomeActivity
    }
    private val user: User by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) arguments?.getSerializable(
            "user",
            User::class.java
        )!!
        else arguments?.getSerializable("user") as User
    }
    private val viewModel: EditProfileViewModel by activityViewModels()

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                binding.ivUser.load(uri)
                Log.d("PhotoPicker", "Selected URI: $uri")
            }
        }

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
            handleViews()
        }
    }

    private fun handleViews() {
        binding.apply {
            ivBack.setOnClickListener {
                nav.navigateUp()
            }
            user.apply {
                if (avatar?.path != "") ivUser.load(avatar?.path)
                etName.setText(name)
                etBio.setText(bio)
                etMail.setText(email)
                ivEditPhoto.setOnClickListener {
                    nav.navigate(R.id.action_editProfileFragment_to_editAvatarDialog)
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

                it.updateAvatarType?.apply {
                    when (name) {
                        EditProfileViewModel.UpdateImagesType.CAMERA.name -> {
                            Log.i(TAG, "CAMERA")
//                            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                            startActivityForResult(intent, 5)
                        }

                        EditProfileViewModel.UpdateImagesType.GALLERY.name -> {
                            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                        }

                        else -> {
                            Log.i(TAG, "ELSE")
                        }
                    }
                }

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 5) {
            data?.data.toString()
            Log.i(TAG, data?.data.toString())
            binding.ivUser.load(data?.data)
        }
    }
}

private const val TAG = "EditProfileFragment"