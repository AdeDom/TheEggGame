package com.adedom.theegggame.ui.login

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.library.data.KEY_DATE
import com.adedom.library.data.KEY_EMPTY
import com.adedom.library.data.KEY_TIME
import com.adedom.library.extension.*
import com.adedom.library.util.getDateTime
import com.adedom.theegggame.R
import com.adedom.theegggame.ui.main.MainActivity
import com.adedom.theegggame.util.*
import com.adedom.theegggame.util.extension.login
import com.theartofdev.edmodo.cropper.CropImage

class RegisterDialog :
    BaseDialogFragment<LoginActivityViewModel>({ R.layout.dialog_add_update }) {

    val TAG = "MyTag"
    private lateinit var mEtUsername: EditText
    private lateinit var mEtPassword: EditText
    private lateinit var mEtRePassword: EditText
    private lateinit var mEtName: EditText
    private lateinit var mRbMale: RadioButton
    private lateinit var mRbFemale: RadioButton
    private lateinit var mIvProfile: ImageView
    private lateinit var mBtRegister: Button
    private var gender = KEY_MALE
    private var mImageUri = KEY_EMPTY

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(LoginActivityViewModel::class.java)

        init(v)

        return AlertDialog.Builder(activity!!)
            .dialogFragment(v, R.drawable.ic_player, R.string.register)
    }

    private fun init(view: View) {
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
            if (mImageUri == KEY_EMPTY) mIvProfile.setImageResource(R.drawable.ic_player)
        }
        mRbFemale.setOnClickListener {
            gender = KEY_FEMALE
            if (mImageUri == KEY_EMPTY) mIvProfile.setImageResource(R.drawable.ic_player_female)
        }

        mIvProfile.setOnClickListener { selectImage() }
        mBtRegister.setOnClickListener { register() }
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
                mImageUri = result.uri.toString()
                mIvProfile.loadCircle(mImageUri)
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
            mEtUsername.verifyLength(4, getString(R.string.error_username_less)) -> return
            mEtPassword.verifyLength(4, getString(R.string.error_password_less)) -> return
            verifyPasswordMatching(
                mEtPassword,
                mEtRePassword,
                getString(R.string.error_password_not_match)
            ) -> return
        }

        //todo upload image
        Log.d(TAG, ">>$mImageUri")

        val username = mEtUsername.getContent()
        val password = mEtPassword.getContent()
        val name = mEtName.getContent()
        val date = getDateTime(KEY_DATE)
        val time = getDateTime(KEY_TIME)
        viewModel.register(username, password, name, mImageUri, date, time, gender)
            .observe(this, Observer {
                if (it.result == KEY_FAILED) {
                    GameActivity.sContext.toast(R.string.username_same_current, Toast.LENGTH_LONG)
                } else {
                    activity!!.login(MainActivity::class.java, it.result!!, username)
                }
            })
    }
}
