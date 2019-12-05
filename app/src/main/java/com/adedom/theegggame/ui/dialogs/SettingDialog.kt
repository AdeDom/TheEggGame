package com.adedom.theegggame.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.adedom.theegggame.R
import com.adedom.theegggame.ui.dialogs.changepassword.ChangePasswordDialog
import com.adedom.theegggame.ui.login.LoginActivity
import com.adedom.theegggame.ui.main.MainActivity
import com.adedom.theegggame.util.BaseActivity
import com.adedom.utility.login

class SettingDialog : DialogFragment() { // 2/12/19

    private lateinit var mBtnChange: Button
    private lateinit var mBtnLogout: Button
    private lateinit var mBtnExit: Button

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity!!.layoutInflater.inflate(R.layout.dialog_setting, null)

        val builder = AlertDialog.Builder(activity!!)
            .setView(view)
            .setIcon(R.drawable.ic_setting)
            .setTitle(R.string.setting)

        init(view)

        return builder.create()
    }

    private fun init(view: View) {
        //todo sound

        //todo update name & profile

        mBtnChange = view.findViewById(R.id.mBtnChange) as Button
        mBtnLogout = view.findViewById(R.id.mBtnLogout) as Button
        mBtnExit = view.findViewById(R.id.mBtnExit) as Button

        mBtnChange.setOnClickListener {
            dialog!!.dismiss()

            val bundle = Bundle()
            bundle.putParcelable("player", MainActivity.sPlayerItem)

            val dialog =
                ChangePasswordDialog()
            dialog.arguments = bundle
            dialog.show(activity!!.supportFragmentManager, null)
        }

        mBtnLogout.setOnClickListener {
            activity!!.login(
                LoginActivity::class.java,
                username = MainActivity.sPlayerItem.username!!
            )
        }

        mBtnExit.setOnClickListener {
            dialog!!.dismiss()
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
                BaseActivity.sActivity.finishAffinity()
            }.show()
    }
}
