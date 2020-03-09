package com.adedom.theegggame.ui.main

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.adedom.library.extension.*
import com.adedom.library.util.BaseDialogFragment
import com.adedom.library.util.KEY_EMPTY
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Player
import com.adedom.theegggame.util.*
import com.adedom.theegggame.util.extension.playSoundClick
import com.theartofdev.edmodo.cropper.CropImage

class ChangeProfileDialog : BaseDialogFragment<MainActivityViewModel>(
    { R.layout.dialog_change_profile },
    { R.drawable.ic_profile },
    { R.string.change_profile }
) {

    private lateinit var mPlayer: Player
    private lateinit var mEtName: EditText
    private lateinit var mRbMale: RadioButton
    private lateinit var mRbFemale: RadioButton
    private lateinit var mIvProfile: ImageView
    private lateinit var mGender: String
    private var mBitMap: Bitmap? = null

    override fun initDialog(view: View) {
        super.initDialog(view)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        mPlayer = MainActivity.sPlayer

        mGender = mPlayer.gender!!

        mEtName = view.findViewById(R.id.mEtName) as EditText
        mRbMale = view.findViewById(R.id.mRbMale) as RadioButton
        mRbFemale = view.findViewById(R.id.mRbFemale) as RadioButton
        mIvProfile = view.findViewById(R.id.mIvProfile) as ImageView
        val btChangeProfile = view.findViewById(R.id.mBtSave) as Button

        mEtName.setText(mPlayer.name!!)
        if (mPlayer.gender == KEY_MALE) mRbMale.isChecked = true else mRbFemale.isChecked = true
        setImageProfile(mIvProfile, mPlayer.image!!, mPlayer.gender!!)

        mRbMale.setOnClickListener {
            mGender = KEY_MALE
            if (mPlayer.image == KEY_EMPTY && mBitMap == null)
                mIvProfile.setImageResource(R.drawable.ic_player)

            GameActivity.sContext.playSoundClick()
        }
        mRbFemale.setOnClickListener {
            mGender = KEY_FEMALE
            if (mPlayer.image == KEY_EMPTY && mBitMap == null)
                mIvProfile.setImageResource(R.drawable.ic_player_female)

            GameActivity.sContext.playSoundClick()
        }

        mIvProfile.setOnClickListener {
            selectImage()
            GameActivity.sContext.playSoundClick()
        }

        btChangeProfile.setOnClickListener {
            changeProfile()
            GameActivity.sContext.playSoundClick()
        }
    }

    private fun selectImage() {
        CropImage.activity()
            .setOutputCompressQuality(50)
            .setRequestedSize(150, 150)
            .setMinCropWindowSize(150, 150)
            .setAspectRatio(1, 1)
            .start(GameActivity.sContext, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                mBitMap = MediaStore.Images.Media.getBitmap(activity!!.contentResolver, result.uri)
                mIvProfile.loadCircle(mBitMap!!)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                GameActivity.sContext.toast(result.error.toString(), Toast.LENGTH_LONG)
            }
        }
    }

    private fun changeProfile() {
        if (mEtName.isEmpty(getString(R.string.error_password))) return

        val name = mEtName.getContent()
        val image = if (mBitMap == null) KEY_EMPTY else mBitMap!!.imageToString()

        viewModel.updateProfile(mPlayer.playerId, name, image, mGender).observe(this, Observer {
            if (it.result == KEY_COMPLETED) {
                dialog!!.dismiss()
                GameActivity.sContext.toast(R.string.successfully)
            }
        })
    }
}
