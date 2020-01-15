package com.adedom.theegggame.ui.main

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.library.extension.*
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Player
import com.adedom.theegggame.util.BaseDialogFragment
import com.adedom.theegggame.util.GameActivity
import com.adedom.utility.COMPLETED
import com.adedom.utility.PLAYER
import com.adedom.utility.checkPassword

class ChangePasswordDialog :
    BaseDialogFragment<MainActivityViewModel>({ R.layout.dialog_change_password }) {

    private lateinit var mPlayer: Player
    private lateinit var mEtOldPassword: EditText
    private lateinit var mEtNewPassword: EditText
    private lateinit var mEtRePassword: EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        mPlayer = arguments!!.getParcelable(PLAYER)!!

        init(bView)

        return AlertDialog.Builder(activity!!)
            .dialogFragment(bView, R.drawable.ic_change, R.string.change_password)
    }

    private fun init(view: View) {
        mEtOldPassword = view.findViewById(R.id.mEtOldPassword) as EditText
        mEtNewPassword = view.findViewById(R.id.mEtNewPassword) as EditText
        mEtRePassword = view.findViewById(R.id.mEtRePassword) as EditText

        val etUsername = view.findViewById(R.id.mEtUsername) as EditText
        val btChangePassword = view.findViewById(R.id.mBtSave) as Button

        etUsername.setText(mPlayer.username)
        btChangePassword.setOnClickListener { changePassword() }
    }

    private fun changePassword() {
        when {
            mEtOldPassword.isEmpty(getString(R.string.error_password)) -> return
            mEtNewPassword.isEmpty(getString(R.string.error_password)) -> return
            mEtRePassword.isEmpty(getString(R.string.error_password)) -> return
            mEtNewPassword.checkLess4(getString(R.string.error_password_less)) -> return
            checkPassword(
                mEtNewPassword,
                mEtRePassword,
                getString(R.string.error_password_not_match)
            ) -> return
        }

        val oldPassword = mEtOldPassword.getContent()
        val newPassword = mEtNewPassword.getContent()

        viewModel.updatePassword(mPlayer.playerId!!, oldPassword, newPassword)
            .observe(this, Observer {
                if (it.result == COMPLETED) {
                    dialog!!.dismiss()
                    GameActivity.sContext.toast(R.string.successfully)
                } else {
                    mEtOldPassword.failed(getString(R.string.password_incorrect))
                }
            })
    }
}
