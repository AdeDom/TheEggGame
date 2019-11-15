package com.adedom.theegggame.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.adedom.theegggame.LoginActivity
import com.adedom.theegggame.R
import com.adedom.utility.MyLibrary
import com.adedom.utility.Pathiphon
import com.google.gson.JsonObject
import com.koushikdutta.ion.Ion
import com.theartofdev.edmodo.cropper.CropImage

class RegisterDialog : DialogFragment() { //15/11/19

    val TAG = "MyTag"
    private lateinit var mEdtUsername: EditText
    private lateinit var mEdtPassword: EditText
    private lateinit var mEdtRePassword: EditText
    private lateinit var mEdtName: EditText
    private lateinit var mImgProfile: ImageView
    private lateinit var mBtnRegister: Button
    private var mImageUri = "empty"

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
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
                MyLibrary.with(LoginActivity.sContext).glide(mImageUri, mImgProfile)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                MyLibrary.with(LoginActivity.sContext).showLong(result.error.toString())
            }
        }
    }

    private fun registerPlayer() {
        when {
            MyLibrary.isEmpty(mEdtUsername, getString(R.string.error_username)) -> return
            MyLibrary.isEmpty(mEdtPassword, getString(R.string.error_password)) -> return
            MyLibrary.isEmpty(mEdtRePassword, getString(R.string.error_re_password)) -> return
            MyLibrary.isEmpty(mEdtName, getString(R.string.error_name)) -> return
            checkLess4(mEdtUsername, getString(R.string.error_username_less)) -> return
            checkLess4(mEdtPassword, getString(R.string.error_password_less)) -> return
            checkPassword(mEdtPassword, mEdtRePassword) -> return
        }

        Log.d(TAG, ">>$mImageUri")

        //todo upload image
//        if (mImageUri != "empty") {
//            MyLibrary.with(LoginActivity.sContext).ionUpload(mImageUri)
//        }

        Ion.with(LoginActivity.sContext)
            .load(Pathiphon.BASE_URL + "register.php")
            .setBodyParameter("values1", mEdtUsername.text.toString().trim())
            .setBodyParameter("values2", mEdtPassword.text.toString().trim())
            .setBodyParameter("values3", mEdtName.text.toString().trim())
            .setBodyParameter("values4", mImageUri)
            .asJsonArray()
            .setCallback { e, result ->
                val jsObject = result.get(0) as JsonObject
                val values = jsObject.get("values1").asString.trim()

                if (values == "failed") {
                    MyLibrary.with(LoginActivity.sContext)
                        .showLong(R.string.username_same_current_player)
                } else {
                    LoginActivity.login(
                        activity!!,
                        LoginActivity.sContext,
                        values,
                        mEdtUsername.text.toString().trim()
                    )
                }
            }
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
