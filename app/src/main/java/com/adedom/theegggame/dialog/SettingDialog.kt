package com.adedom.theegggame.dialog

import android.app.Dialog
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.appcompat.app.AlertDialog
import android.view.View
import android.widget.Button
import com.adedom.theegggame.LoginActivity
import com.adedom.theegggame.MainActivity
import com.adedom.theegggame.R

class SettingDialog : DialogFragment() {

    private lateinit var mBtnChange: Button
    private lateinit var mBtnLogout: Button
    private lateinit var mBtnExit: Button

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity!!.layoutInflater.inflate(R.layout.dialog_setting, null)

        val builder = AlertDialog.Builder(activity!!)
            .setView(view)
            .setIcon(R.drawable.ic_setting)
            .setTitle(R.string.setting)

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
            activity!!.getSharedPreferences(MainActivity.MY_LOGIN, MODE_PRIVATE).edit()
                .putString("player_id", "empty")
                .apply()
            startActivity(
                Intent(MainActivity.context, LoginActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
            activity!!.finishAffinity()
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
                MainActivity.activity.finishAffinity()
            }.show()
    }
}
