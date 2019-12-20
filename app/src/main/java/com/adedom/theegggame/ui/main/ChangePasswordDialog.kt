package com.adedom.theegggame.ui.main

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Player
import com.adedom.theegggame.util.BaseDialogFragment
import com.adedom.theegggame.util.GameActivity
import com.adedom.utility.COMPLETED
import com.adedom.utility.PLAYER
import com.adedom.utility.checkPassword
import com.adedom.utility.extension.*

class ChangePasswordDialog :
    BaseDialogFragment<MainActivityViewModel>({ R.layout.dialog_change_password }) {

    private lateinit var mPlayer: Player
    private lateinit var mEdtUsername: EditText
    private lateinit var mEdtOldPassword: EditText
    private lateinit var mEdtNewPassword: EditText
    private lateinit var mEdtRePassword: EditText
    private lateinit var mBtnChangePassword: Button

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        mPlayer = arguments!!.getParcelable(PLAYER)!!

        init(bView)

        return AlertDialog.Builder(activity!!)
            .dialog(bView, R.drawable.ic_change, R.string.change_password)
    }

    private fun init(view: View) {
        mEdtUsername = view.findViewById(R.id.mEtUsername) as EditText
        mEdtOldPassword = view.findViewById(R.id.mEtOldPassword) as EditText
        mEdtNewPassword = view.findViewById(R.id.mEtNewPassword) as EditText
        mEdtRePassword = view.findViewById(R.id.mEtRePassword) as EditText
        mBtnChangePassword = view.findViewById(R.id.mBtSave) as Button

        mEdtUsername.setText(mPlayer.username)

        mBtnChangePassword.setOnClickListener { changePassword() }
    }

    private fun changePassword() {
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

        val oldPassword = mEdtOldPassword.getContent()
        val newPassword = mEdtNewPassword.getContent()

        viewModel.updatePassword(mPlayer.playerId!!, oldPassword, newPassword)
            .observe(this, Observer {
                if (it.result == COMPLETED) {
                    dialog!!.dismiss()
                    GameActivity.sContext.toast(R.string.successfully)
                } else {
                    mEdtOldPassword.failed(getString(R.string.password_incorrect))
                }
            })
    }
}
