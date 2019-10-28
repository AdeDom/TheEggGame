package com.adedom.theegggame.dialog

import android.app.Dialog
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Button
import com.adedom.theegggame.LoginActivity
import com.adedom.theegggame.MainActivity
import com.adedom.theegggame.R
import com.adedom.theegggame.utility.MyIntent

class SettingDialog : DialogFragment() { // 15/7/62

    val TAG = "SettingDialog"
    private lateinit var mBtnChange: Button
    private lateinit var mBtnLogout: Button
    private lateinit var mBtnExit: Button

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity!!.layoutInflater.inflate(R.layout.dialog_setting, null)

        val builder = AlertDialog.Builder(activity!!)
            .setView(view)
            .setIcon(R.drawable.ic_setting)
            .setTitle("Setting")

        bindWidgets(view)
        setEvents()

        return builder.create()
    }

    private fun bindWidgets(view: View) {
        mBtnChange = view.findViewById(R.id.mBtnChange) as Button
        mBtnLogout = view.findViewById(R.id.mBtnLogout) as Button
        mBtnExit = view.findViewById(R.id.mBtnExit) as Button
    }

    private fun setEvents() {
        mBtnChange.setOnClickListener {
            dialog.dismiss()

            val bundle = Bundle()
            bundle.putParcelable("player", MainActivity.mPlayerItem)

            val dialog = UpdatePlayerDialog()
            dialog.arguments = bundle
            dialog.show(activity!!.supportFragmentManager, null)
        }

        mBtnLogout.setOnClickListener {
            val editor = activity!!.getSharedPreferences(LoginActivity.MY_LOGIN, MODE_PRIVATE).edit()
            editor.putString("password", "").commit()
            activity!!.finish()
            MyIntent().getIntent(activity!!, LoginActivity::class.java)
        }

        mBtnExit.setOnClickListener {
            dialog.dismiss()
            exitDialog()
        }
    }

    private fun exitDialog() {
        val builder = AlertDialog.Builder(activity!!)
        builder.setTitle("Exit")
            .setMessage("Confirm exit?")
            .setIcon(R.drawable.ic_exit)
            .setPositiveButton(R.string.no) { dialog, which -> dialog.dismiss() }
            .setNegativeButton(R.string.yes) { dialog, which ->
                MainActivity.mActivity.finishAffinity()
            }.show()
    }
}
