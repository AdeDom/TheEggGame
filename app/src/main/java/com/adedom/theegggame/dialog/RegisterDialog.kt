package com.adedom.theegggame.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.theegggame.LoginActivity
import com.adedom.theegggame.MainActivity
import com.adedom.theegggame.R
import com.adedom.theegggame.viewmodels.RegisterDialogViewModel
import com.adedom.utility.isEmpty
import com.adedom.utility.loadCircle
import com.adedom.utility.login
import com.adedom.utility.toast
import com.theartofdev.edmodo.cropper.CropImage

class RegisterDialog : DialogFragment() {

    val TAG = "MyTag"
    private lateinit var mViewModel: RegisterDialogViewModel
    private lateinit var mEdtUsername: EditText
    private lateinit var mEdtPassword: EditText
    private lateinit var mEdtRePassword: EditText
    private lateinit var mEdtName: EditText
    private lateinit var mImgProfile: ImageView
    private lateinit var mBtnRegister: Button
    private var mImageUri = "empty"

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        mViewModel = ViewModelProviders.of(this).get(RegisterDialogViewModel::class.java)

        val view = activity!!.layoutInflater.inflate(R.layout.dialog_add_update, null)

        val builder = AlertDialog.Builder(activity!!)
            .setView(view)
            .setIcon(R.drawable.ic_player)
            .setTitle(R.string.register)

        bindWidgets(view)
        setEvents()

        return builder.create()
    }

    private fun bindWidgets(view: View) {
        mEdtUsername = view.findViewById(R.id.mEdtUsername) as EditText
        mEdtPassword = view.findViewById(R.id.mEdtPassword) as EditText
        mEdtRePassword = view.findViewById(R.id.mEdtRePassword) as EditText
        mEdtName = view.findViewById(R.id.mEdtName) as EditText
        mImgProfile = view.findViewById(R.id.mImgProfile) as ImageView
        mBtnRegister = view.findViewById(R.id.mBtnSave) as Button
    }

    private fun setEvents() {
        mImgProfile.setOnClickListener { selectImage() }
        mBtnRegister.setOnClickListener { registerPlayer() }
    }

    private fun selectImage() {
        CropImage.activity()
            .setOutputCompressQuality(50)
            .setRequestedSize(150, 150)
            .setMinCropWindowSize(150, 150)
            .setAspectRatio(1, 1)
            .start(LoginActivity.sContext, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                mImageUri = result.uri.toString()
                mImgProfile.loadCircle(mImageUri)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                LoginActivity.sContext.toast(result.error.toString(), Toast.LENGTH_LONG)
            }
        }
    }

    private fun registerPlayer() {
        when {
            mEdtUsername.isEmpty(getString(R.string.error_username)) -> return
            mEdtPassword.isEmpty(getString(R.string.error_password)) -> return
            mEdtRePassword.isEmpty(getString(R.string.error_re_password)) -> return
            mEdtName.isEmpty(getString(R.string.error_name)) -> return
            checkLess4(mEdtUsername, getString(R.string.error_username_less)) -> return
            checkLess4(mEdtPassword, getString(R.string.error_password_less)) -> return
            checkPassword(mEdtPassword, mEdtRePassword) -> return
        }

        //todo upload image
//        if (mImageUri != "empty") {
//            MyLibrary.with(LoginActivity.sContext).ionUpload(mImageUri)
//        }

        val username = mEdtUsername.text.toString().trim()
        val password = mEdtPassword.text.toString().trim()
        val name = mEdtName.text.toString().trim()

        mViewModel.repository.registerPlayer(username, password, name, mImageUri)
        mViewModel.result.observe(this, Observer {
            if (it.playerId == "failed") {
                LoginActivity.sContext.toast(R.string.username_same_current, Toast.LENGTH_LONG)
            } else {
                activity!!.login(MainActivity::class.java, it.playerId!!, username)
            }
        })
    }

    private fun checkLess4(editText: EditText, error: String): Boolean {
        if (editText.text.toString().trim().length < 4) {
            editText.requestFocus()
            editText.error = error
            return true
        }
        return false
    }

    private fun checkPassword(editText1: EditText, editText2: EditText): Boolean {
        val edt1 = editText1.text.toString().trim()
        val edt2 = editText2.text.toString().trim()
        if (edt1 != edt2) {
            editText2.requestFocus()
            editText2.error = getString(R.string.error_password_not_match)
            return true
        }
        return false
    }
}
