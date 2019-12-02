package com.adedom.theegggame.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Player
import com.adedom.theegggame.ui.activities.MainActivity
import com.adedom.theegggame.ui.viewmodels.ChangePasswordDialogViewModel
import com.adedom.utility.checkLess4
import com.adedom.utility.checkPassword
import com.adedom.utility.isEmpty
import com.adedom.utility.toast

class ChangePasswordDialog : DialogFragment() { // 2/12/19

    val TAG = "UpdatePlayerDialog"
    private lateinit var mViewModel: ChangePasswordDialogViewModel
    private lateinit var mPlayer: Player
    private lateinit var mEdtUsername: EditText
    private lateinit var mEdtOldPassword: EditText
    private lateinit var mEdtNewPassword: EditText
    private lateinit var mEdtRePassword: EditText
    private lateinit var mBtnChangePassword: Button

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        mViewModel = ViewModelProviders.of(this).get(ChangePasswordDialogViewModel::class.java)

        val view = activity!!.layoutInflater.inflate(R.layout.dialog_change_password, null)

        mPlayer = arguments!!.getParcelable("player")!!

        val builder = AlertDialog.Builder(activity!!)
            .setView(view)
            .setIcon(R.drawable.ic_change)
            .setTitle(R.string.change_password)

        init(view)

        return builder.create()
    }

    private fun init(view: View) {
        mEdtUsername = view.findViewById(R.id.mEdtUsername) as EditText
        mEdtOldPassword = view.findViewById(R.id.mEdtOldPassword) as EditText
        mEdtNewPassword = view.findViewById(R.id.mEdtNewPassword) as EditText
        mEdtRePassword = view.findViewById(R.id.mEdtRePassword) as EditText
        mBtnChangePassword = view.findViewById(R.id.mBtnSave) as Button

        mEdtUsername.setText(mPlayer.username)

        mBtnChangePassword.setOnClickListener { updatePlayer() }
    }

    private fun updatePlayer() {
        when {
            mEdtOldPassword.isEmpty(getString(R.string.error_password)) -> return
            mEdtNewPassword.isEmpty(getString(R.string.error_password)) -> return
            mEdtRePassword.isEmpty(getString(R.string.error_password)) -> return
            mEdtNewPassword.checkLess4(getString(R.string.error_password_less)) -> return
            checkPassword(
                mEdtNewPassword,
                mEdtRePassword,
                getString(R.string.error_password_not_match)
            ) -> return
        }

        val oldPassword = mEdtOldPassword.text.toString().trim()
        val newPassword = mEdtNewPassword.text.toString().trim()

        mViewModel.response.changePassword(mPlayer.playerId!!, oldPassword, newPassword)
        mViewModel.result().observe(this, Observer {
            if (it.result == "failed") {
                mEdtOldPassword.requestFocus()
                mEdtOldPassword.error = getString(R.string.password_incorrect)
            } else {
                dialog!!.dismiss()
                MainActivity.sContext.toast(R.string.successfully)
            }
        })

        // TODO: 23/05/2562 refresh MainActivity
    }
}
