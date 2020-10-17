package com.adedom.theegggame.ui.login

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
import com.adedom.theegggame.ui.main.MainActivity
import com.adedom.theegggame.util.GameActivity
import com.adedom.theegggame.util.KEY_FAILED
import com.adedom.theegggame.util.KEY_FEMALE
import com.adedom.theegggame.util.KEY_MALE
import com.adedom.theegggame.util.extension.loginSuccess
import com.adedom.theegggame.util.extension.playSoundClick
import com.theartofdev.edmodo.cropper.CropImage

class RegisterDialog : BaseDialogFragment<LoginActivityViewModel>(
    { R.layout.dialog_register },
    { R.drawable.ic_player },
    { R.string.register }
) {

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

    override fun initDialog(view: View) {
        super.initDialog(view)
        viewModel = ViewModelProvider(this).get(LoginActivityViewModel::class.java)

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

        val username = mEtUsername.getContent()
        val password = mEtPassword.getContent()
        val name = mEtName.getContent()
        val image = if (mBitMap == null) KEY_EMPTY else mBitMap!!.imageToString()
//        viewModel.register(username, password, name, image, gender)
//            .observe(this, Observer {
//                if (it.result == KEY_FAILED) {
//                    GameActivity.sContext.toast(R.string.username_same_current, Toast.LENGTH_LONG)
//                } else {
//                    activity!!.loginSuccess(MainActivity::class.java, it.result!!, username)
//                }
//            })
    }

}
