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
import com.adedom.theegggame.util.GameActivity
import com.adedom.utility.PLAYER
import com.adedom.utility.dialog
import com.adedom.utility.exitDialog
import com.adedom.utility.login

class SettingDialog : DialogFragment() {

    private lateinit var mBtnChange: Button
    private lateinit var mBtnLogout: Button
    private lateinit var mBtnExit: Button

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        val view = activity!!.layoutInflater.inflate(R.layout.dialog_setting, null)

        init(view)

        return AlertDialog.Builder(activity!!).dialog(view, R.drawable.ic_setting, R.string.setting)
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
            bundle.putParcelable(PLAYER, MainActivity.sPlayerItem)

            val dialog = ChangePasswordDialog()
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
            AlertDialog.Builder(activity!!).exitDialog { GameActivity.sActivity.finishAffinity() }
        }
    }
}
