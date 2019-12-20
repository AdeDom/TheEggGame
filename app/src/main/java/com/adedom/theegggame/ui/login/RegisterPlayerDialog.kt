package com.adedom.theegggame.ui.login

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.theegggame.R
import com.adedom.theegggame.ui.main.MainActivity
import com.adedom.theegggame.util.BaseDialogFragment
import com.adedom.theegggame.util.GameActivity
import com.adedom.utility.*
import com.adedom.utility.extension.*
import com.theartofdev.edmodo.cropper.CropImage

class RegisterPlayerDialog :
    BaseDialogFragment<LoginActivityViewModel>({ R.layout.dialog_add_update }) {

    private lateinit var mEdtUsername: EditText
    private lateinit var mEdtPassword: EditText
    private lateinit var mEdtRePassword: EditText
    private lateinit var mEdtName: EditText
    private lateinit var mImgProfile: ImageView
    private lateinit var mBtnRegister: Button
    private var mImageUri = "empty"

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(LoginActivityViewModel::class.java)

        init(bView)

        return AlertDialog.Builder(activity!!).dialog(bView, R.drawable.ic_player, R.string.register)
    }

    private fun init(view: View) {
        mEdtUsername = view.findViewById(R.id.mEdtUsername) as EditText
        mEdtPassword = view.findViewById(R.id.mEdtPassword) as EditText
        mEdtRePassword = view.findViewById(R.id.mEdtRePassword) as EditText
        mEdtName = view.findViewById(R.id.mEdtName) as EditText
        mImgProfile = view.findViewById(R.id.mImgProfile) as ImageView
        mBtnRegister = view.findViewById(R.id.mBtnSave) as Button

        mImgProfile.setOnClickListener { selectImage() }
        mBtnRegister.setOnClickListener { registerPlayer() }
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
                mImgProfile.loadCircle(mImageUri)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                GameActivity.sContext.toast(result.error.toString(), Toast.LENGTH_LONG)
            }
        }
    }

    private fun registerPlayer() {
        when {
            mEdtUsername.isEmpty(getString(R.string.error_username)) -> return
            mEdtPassword.isEmpty(getString(R.string.error_password)) -> return
            mEdtRePassword.isEmpty(getString(R.string.error_re_password)) -> return
            mEdtName.isEmpty(getString(R.string.error_name)) -> return
            mEdtUsername.checkLess4(getString(R.string.error_username_less)) -> return
            mEdtPassword.checkLess4(getString(R.string.error_password_less)) -> return
            checkPassword(
                mEdtPassword,
                mEdtRePassword,
                getString(R.string.error_password_not_match)
            ) -> return
        }

        //todo upload image
        Log.d(TAG, ">>$mImageUri")

        val username = mEdtUsername.getContent()
        val password = mEdtPassword.getContent()
        val name = mEdtName.getContent()
        val date = getDateTime(DATE)
        val time = getDateTime(TIME)

        viewModel.insertPlayer(username, password, name, mImageUri, date, time)
            .observe(this, Observer {
                if (it.result == FAILED) {
                    GameActivity.sContext.toast(R.string.username_same_current, Toast.LENGTH_LONG)
                } else {
                    activity!!.login(MainActivity::class.java, it.result!!, username)
                }
            })
    }
}
