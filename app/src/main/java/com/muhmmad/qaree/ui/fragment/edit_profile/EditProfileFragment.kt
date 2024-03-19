package com.muhmmad.qaree.ui.fragment.edit_profile

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import coil.load
import com.muhmmad.domain.model.User
import com.muhmmad.qaree.databinding.FragmentEditProfileBinding

class EditProfileFragment : Fragment() {
    private val binding: FragmentEditProfileBinding by lazy {
        FragmentEditProfileBinding.inflate(layoutInflater)
    }
    private val nav: NavController by lazy {
        findNavController()
    }
    private val user: User by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) arguments?.getSerializable(
            "user",
            User::class.java
        )!!
        else arguments?.getSerializable("user") as User
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
            handleViews()
        }
    }

    private fun handleViews() {
        binding.apply {
            ivBack.setOnClickListener {
                nav.navigateUp()
            }
            user.apply {
                ivUser.load(avatar?.path)
                etName.setText(name)
                etBio.setText(bio)
                etMail.setText(email)
                ivEditPhoto.setOnClickListener {

                }
            }
        }
    }
}