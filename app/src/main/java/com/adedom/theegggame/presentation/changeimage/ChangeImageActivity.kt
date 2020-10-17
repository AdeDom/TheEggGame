package com.adedom.theegggame.presentation.changeimage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.adedom.teg.presentation.changeimage.ChangeImageViewModel
import com.adedom.theegggame.R
import com.adedom.theegggame.base.BaseActivity
import com.adedom.theegggame.util.extension.setImageCircle
import com.adedom.theegggame.util.extension.toast
import kotlinx.android.synthetic.main.activity_change_image.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChangeImageActivity : BaseActivity() {

    val viewModel by viewModel<ChangeImageViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_image)

        viewModel.state.observe { state ->
            progressBar.visibility = if (state.loading) View.VISIBLE else View.INVISIBLE

            btChangeImageProfile.isEnabled = state.isImageUri
        }

        viewModel.fetchPlayerInfo.observe(this, { playerInfo ->
            if (playerInfo == null) return@observe

            ivImageProfile.setImageCircle(playerInfo.image)
        })

        viewModel.changeImageProfileEvent.observe { response ->
            toast(response.message)
            if (response.success) finish()
        }

        ivImageProfile.setOnClickListener {
            Intent(Intent.ACTION_PICK).apply {
                type = "image/*"
                val mimeTypes = arrayOf("image/jpeg", "image/png")
                putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
                startActivityForResult(this, REQUEST_CODE_IMAGE)
            }
        }

        btChangeImageProfile.setOnClickListener {
            viewModel.callChangeImageProfile()
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
