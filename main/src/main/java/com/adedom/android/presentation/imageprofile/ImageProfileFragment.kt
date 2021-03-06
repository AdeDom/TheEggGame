package com.adedom.android.presentation.imageprofile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.adedom.android.R
import com.adedom.android.base.BaseFragment
import com.adedom.android.presentation.changeimage.ChangeImageFragment
import com.adedom.android.util.setImageCircle
import com.adedom.android.util.setVisibility
import com.adedom.android.util.snackbar
import com.adedom.teg.presentation.imageprofile.ImageProfileViewModel
import kotlinx.android.synthetic.main.fragment_image_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ImageProfileFragment : BaseFragment(R.layout.fragment_image_profile) {

    private val viewModel by viewModel<ImageProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe { state ->
            animationViewLoading.setVisibility(state.loading)

            btUploadImageProfile.isEnabled = state.isImageUri

            btUploadImageProfile.isClickable = state.isClickable
        }

        viewModel.uploadImageProfileEvent.observe { response ->
            if (response.success) {
                findNavController().navigate(R.id.action_global_splashScreenFragment)
            } else {
                requireView().snackbar(response.message)
            }
        }

        // event
        tvSkip.setOnClickListener {
            findNavController().navigate(R.id.action_global_splashScreenFragment)
        }

        ivImageProfile.setOnClickListener { selectImage() }
        btSelectImageProfile.setOnClickListener { selectImage() }

        btUploadImageProfile.setOnClickListener {
            viewModel.callUploadImageProfile()
        }

        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_global_splashScreenFragment)
                }
            })
    }

    private fun selectImage() {
        Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(this, ChangeImageFragment.REQUEST_CODE_IMAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri = data.data.toString()
            viewModel.setStateImage(imageUri)
            ivImageProfile.setImageCircle(imageUri)
        }
    }

    companion object {
        const val REQUEST_CODE_IMAGE = 1
    }

}
