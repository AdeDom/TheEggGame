package com.adedom.theegggame.dialog

import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.adedom.theegggame.R
import com.adedom.theegggame.model.PlayerItem
import com.adedom.theegggame.utility.MyConnect
import com.adedom.theegggame.utility.MyIon
import com.adedom.theegggame.utility.MyToast
import com.iceteck.silicompressorr.SiliCompressor
import com.vlk.multimager.activities.GalleryActivity
import com.vlk.multimager.utils.Constants.*
import com.vlk.multimager.utils.Image
import com.vlk.multimager.utils.Params
import java.io.File

class UpdatePlayerDialog : DialogFragment() { // 15/7/62

    val TAG = "UpdatePlayerDialog"
    private lateinit var mPlayerItem: PlayerItem
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
        mEdtUser = view.findViewById(R.id.mEdtUser) as EditText
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
        val intent = Intent(activity, GalleryActivity::class.java)
        val params = Params()
        params.pickerLimit = 1
        intent.putExtra(KEY_PARAMS, params)
        startActivityForResult(intent, TYPE_MULTI_PICKER)
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
                "WHERE id = '${mPlayerItem.id}'"
        MyConnect.executeQuery(sql)

        dialog.dismiss()
        MyToast.showShort(context!!, "บันทึกข้อมูลเรียบร้อย")

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
            MyToast.showLong(context!!, errMsg)
            return true
        }
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != RESULT_OK) {
            return
        }

        if (requestCode == TYPE_MULTI_PICKER) {
            val images = data!!.getParcelableArrayListExtra<Image>(KEY_BUNDLE_LIST)
            var bmp: Bitmap? = null
            mImagePath = images[0].imagePath
            try {
                bmp = SiliCompressor.with(context).getCompressBitmap(mImagePath)

                val filePath = SiliCompressor.with(context).compress(mImagePath, File(mImagePath))
                mImgName = filePath.substring(mImagePath.length + 1)
                mImagePath = "/storage/emulated/0/Silicompressor/images/$mImgName"

            } catch (ex: Exception) {
            }

            mImgProfile.setImageBitmap(bmp)
        }
    }
}
