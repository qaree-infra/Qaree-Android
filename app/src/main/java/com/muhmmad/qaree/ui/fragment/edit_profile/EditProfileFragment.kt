package com.muhmmad.qaree.ui.fragment.edit_profile

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
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
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

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
    private var user: User? = null
    private val viewModel: EditProfileViewModel by activityViewModels()

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                binding.ivUser.load(uri)
                val bitmap = MediaStore.Images.Media.getBitmap(activity.contentResolver, uri)
                viewModel.uploadUserAvatar(getImageFile(bitmap))
            } else activity.showMessage(getString(R.string.no_image_selected))
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
            user =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) arguments?.getParcelable(
                    "user",
                    User::class.java
                )!!
                else arguments?.getParcelable("user")
            checkState()
            handleViews()
        }
    }

    private fun handleViews() {
        binding.apply {
            ivBack.setOnClickListener {
                nav.navigateUp()
            }
            user?.apply {
                if (avatar?.path != "") ivUser.load(avatar?.path) {
                    placeholder(R.drawable.ic_profile_avatar)
                }
                etName.setText(name)
                etBio.setText(bio)
                etMail.setText(email)
                ivEditPhoto.setOnClickListener {
                    nav.navigate(R.id.action_editProfileFragment_to_editAvatarDialog)
                }
                etName.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putBoolean("isName", true)
                    bundle.putString("text", name)
                    nav.navigate(R.id.action_editProfileFragment_to_editNameDialog, bundle)
                }
                etBio.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putBoolean("isName", false)
                    bundle.putString("text", bio)
                    nav.navigate(R.id.action_editProfileFragment_to_editNameDialog, bundle)
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
                            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            startActivityForResult(intent, 5)
                        }

                        EditProfileViewModel.UpdateImagesType.GALLERY.name -> {
                            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                        }
                    }
                }

                it.userData?.apply {
                    binding.etName.setText(name.toString())
                    binding.etMail.setText(email.toString())
                    binding.etBio.setText(bio.toString())
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 5) {
            val image = data?.extras?.get("data") as Bitmap
            binding.ivUser.load(image)
            viewModel.uploadUserAvatar(getImageFile(image))
        }
    }

    private fun getImageFile(bitmap: Bitmap): MultipartBody.Part {
        // Create a file to write bitmap data
        val file = File(context?.cacheDir, "avatar")
        file.createNewFile()

        // Convert bitmap to byte array
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
        val bitmapData = bos.toByteArray()

        // Write the bytes to the file
        val fos = FileOutputStream(file)
        fos.write(bitmapData)
        fos.flush()
        fos.close()
        // Create a request body from the file
        val reqFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("avatar", file.name, reqFile)
    }
}

private const val TAG = "EditProfileFragment"