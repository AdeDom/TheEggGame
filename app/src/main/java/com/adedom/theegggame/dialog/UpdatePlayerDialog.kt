package com.adedom.theegggame.dialog

import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.adedom.theegggame.MainActivity
import com.adedom.theegggame.R
import com.adedom.theegggame.model.PlayerBean
import com.adedom.theegggame.utility.MyConnect
import com.adedom.theegggame.utility.MyIon
import com.adedom.utility.MyLibrary

class UpdatePlayerDialog : DialogFragment() {

    val TAG = "UpdatePlayerDialog"
    private lateinit var mPlayerItem: PlayerBean
    private lateinit var mEdtUser: EditText
    private lateinit var mEdtPassword: EditText
    private lateinit var mEdtRePassword: EditText
    private lateinit var mEdtName: EditText
    private lateinit var mImgProfile: ImageView
    private lateinit var mBtnSave: Button
    private var mImagePath = ""
    private var mImgName = ""

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity!!.layoutInflater.inflate(R.layout.dialog_player, null)

        mPlayerItem = arguments!!.getParcelable("player")

        val builder = AlertDialog.Builder(activity!!)
            .setView(view)
            .setIcon(R.drawable.ic_change)
            .setTitle("Update player")

        bindWidgets(view)
        setWidgets()
        setEvents()

        return builder.create()
    }

    private fun bindWidgets(view: View) {
        mEdtUser = view.findViewById(R.id.mEdtUsername) as EditText
        mEdtPassword = view.findViewById(R.id.mEdtPassword) as EditText
        mEdtRePassword = view.findViewById(R.id.mEdtRePassword) as EditText
        mEdtName = view.findViewById(R.id.mEdtName) as EditText
        mImgProfile = view.findViewById(R.id.mImgProfile) as ImageView
        mBtnSave = view.findViewById(R.id.mBtnSave) as Button
    }

    private fun setWidgets() {
        mEdtUser.isEnabled = false
        mEdtUser.setText(mPlayerItem.user)
        mEdtName.setText(mPlayerItem.name)

        if (mPlayerItem.image.isNotEmpty()) {
            MyIon.getIon(mImgProfile, mPlayerItem.image)
        }
        mBtnSave.text = "Update player"
    }

    private fun setEvents() {
        mImgProfile.setOnClickListener { selectImage() }
        mBtnSave.setOnClickListener { updatePlayer() }
    }

    private fun selectImage() {
//        val intent = Intent(activity, GalleryActivity::class.java)
//        val params = Params()
//        params.pickerLimit = 1
//        intent.putExtra(KEY_PARAMS, params)
//        startActivityForResult(intent, TYPE_MULTI_PICKER)
    }

    private fun updatePlayer() {
        if (checkIsEmpty()) {
            return
        }

        if (mImagePath.isNotEmpty()) {
            MyIon.getIon(context!!, mImagePath) // upload image
        } else {
            mImgName = mPlayerItem.image
        }

        val sql = "UPDATE tbl_player SET \n" +
                "user = '${mEdtUser.text.toString().trim()}',\n" +
                "password = '${mEdtPassword.text.toString().trim()}',\n" +
                "name = '${mEdtName.text.toString().trim()}',\n" +
                "image = '$mImgName'\n" +
                "WHERE id = '${mPlayerItem.playerId}'"
        MyConnect.executeQuery(sql)

        dialog.dismiss()
        MyLibrary.with(MainActivity.sContext).showShort(R.string.recording_complete)

        // TODO: 23/05/2562 refresh MainActivity
        //todo password activity login
    }

    private fun checkIsEmpty(): Boolean {
        val user = mEdtUser.text.toString().trim()
        val password = mEdtPassword.text.toString().trim()
        val rePassword = mEdtRePassword.text.toString().trim()
        val name = mEdtName.text.toString().trim()

        var errMsg = ""
        when {
            user.length < 4 || password.length < 4 ->
                errMsg = "กรุณากรอกชื่อผู้ใช้งานและรหัสผ่าน\nอย่าน้อย 4 ตัวอักษรขึ้นไป"
            password != rePassword -> errMsg = "รหัสผ่านไม่ตรงกัน\nกรุณากรอกรหัสผ่านอีกครั้ง"
            user == "" -> errMsg = "กรุณากรอกชื่อผู้ใช้งาน"
            password == "" -> errMsg = "กรุณากรอกรหัสผ่าน"
            rePassword == "" -> errMsg = "กรุณากรอกรหัสผ่านอีกครั้ง"
            name == "" -> errMsg = "กรุณากรอกชื่อโปรไฟล์"
        }

        if (errMsg != "") {
            MyLibrary.with(MainActivity.sContext).showLong(errMsg)
            return true
        }
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != RESULT_OK) {
            return
        }

//        if (requestCode == TYPE_MULTI_PICKER) {
//            val images = data!!.getParcelableArrayListExtra<Image>(KEY_BUNDLE_LIST)
//            var bmp: Bitmap? = null
//            mImagePath = images[0].imagePath
//            try {
//                bmp = SiliCompressor.with(contexts).getCompressBitmap(mImagePath)
//
//                val filePath = SiliCompressor.with(contexts).compress(mImagePath, File(mImagePath))
//                mImgName = filePath.substring(mImagePath.length + 1)
//                mImagePath = "/storage/emulated/0/Silicompressor/images/$mImgName"
//
//            } catch (ex: Exception) {
//            }
//
//            mImgProfile.setImageBitmap(bmp)
//        }
    }
}
