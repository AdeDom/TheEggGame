package com.adedom.android.presentation.changeimage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.adedom.android.R
import com.adedom.android.base.BaseFragment
import com.adedom.android.util.setImageCircle
import com.adedom.android.util.setVisibility
import com.adedom.android.util.snackbar
import com.adedom.teg.presentation.changeimage.ChangeImageViewModel
import kotlinx.android.synthetic.main.fragment_change_image.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChangeImageFragment : BaseFragment(R.layout.fragment_change_image) {

    private val viewModel by viewModel<ChangeImageViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe { state ->
            progressBar.setVisibility(state.loading)

            btChangeImageProfile.isEnabled = state.isImageUri
        }

        viewModel.fetchPlayerInfo.observe(viewLifecycleOwner, { playerInfo ->
            if (playerInfo == null) return@observe

            ivImageProfile.setImageCircle(playerInfo.image)
        })

        viewModel.changeImageProfileEvent.observe { response ->
            rootLayout.snackbar(response.message)
            if (response.success) {
                findNavController().popBackStack()
            }
        }

        ivImageProfile.setOnClickListener { selectImage() }
        btSelectImageProfile.setOnClickListener { selectImage() }

        btChangeImageProfile.setOnClickListener {
            viewModel.callChangeImageProfile()
        }
    }

    private fun selectImage() {
        Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(this, REQUEST_CODE_IMAGE)
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
