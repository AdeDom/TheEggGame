package com.adedom.theegggame.ui.login

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.adedom.library.extension.*
import com.adedom.theegggame.R
import com.adedom.theegggame.util.GameActivity
import com.adedom.theegggame.util.KEY_FEMALE
import com.adedom.theegggame.util.KEY_MALE
import com.adedom.theegggame.util.extension.playSoundClick
import com.theartofdev.edmodo.cropper.CropImage

class RegisterDialog : DialogFragment() {

    private lateinit var mEtUsername: EditText
    private lateinit var mEtPassword: EditText
    private lateinit var mEtRePassword: EditText
    private lateinit var mEtName: EditText
    private lateinit var mRbMale: RadioButton
    private lateinit var mRbFemale: RadioButton
    private lateinit var mIvProfile: ImageView
    private lateinit var mBtRegister: Button
    private var gender = KEY_MALE
    private var mBitMap: Bitmap? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mEtUsername = view.findViewById(R.id.mEtUsername) as EditText
        mEtPassword = view.findViewById(R.id.mEtPassword) as EditText
        mEtRePassword = view.findViewById(R.id.mEtRePassword) as EditText
        mEtName = view.findViewById(R.id.mEtName) as EditText
        mRbMale = view.findViewById(R.id.mRbMale) as RadioButton
        mRbFemale = view.findViewById(R.id.mRbFemale) as RadioButton
        mIvProfile = view.findViewById(R.id.mIvProfile) as ImageView
        mBtRegister = view.findViewById(R.id.mBtSave) as Button

        mRbMale.setOnClickListener {
            gender = KEY_MALE
            if (mBitMap == null) mIvProfile.setImageResource(R.drawable.ic_player)

            GameActivity.sContext.playSoundClick()
        }
        mRbFemale.setOnClickListener {
            gender = KEY_FEMALE
            if (mBitMap == null) mIvProfile.setImageResource(R.drawable.ic_player_female)

            GameActivity.sContext.playSoundClick()
        }

        mIvProfile.setOnClickListener {
            selectImage()
            GameActivity.sContext.playSoundClick()
        }

        mBtRegister.setOnClickListener {
            register()
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
//                mBitMap = MediaStore.Images.Media.getBitmap(activity!!.contentResolver, result.uri)
                mIvProfile.loadCircle(mBitMap!!)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                GameActivity.sContext.toast(result.error.toString(), Toast.LENGTH_LONG)
            }
        }
    }

    private fun register() {
        when {
            mEtUsername.isEmpty(getString(R.string.error_username)) -> return
            mEtPassword.isEmpty(getString(R.string.error_password)) -> return
            mEtRePassword.isEmpty(getString(R.string.error_re_password)) -> return
            mEtName.isEmpty(getString(R.string.error_name)) -> return
            mEtUsername.isLength(4, getString(R.string.error_username_less)) -> return
            mEtPassword.isLength(4, getString(R.string.error_password_less)) -> return
            mEtPassword.isMatching(
                mEtRePassword,
                getString(R.string.error_password_not_match)
            ) -> return
        }
    }

}
