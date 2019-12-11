package com.adedom.theegggame.ui.dialogs.changepassword

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
import com.adedom.theegggame.data.networks.PlayerApi
import com.adedom.theegggame.data.repositories.PlayerRepository
import com.adedom.theegggame.util.GameActivity
import com.adedom.utility.*

class ChangePasswordDialog : DialogFragment() {

    private lateinit var mViewModel: ChangePasswordDialogViewModel
    private lateinit var mPlayer: Player
    private lateinit var mEdtUsername: EditText
    private lateinit var mEdtOldPassword: EditText
    private lateinit var mEdtNewPassword: EditText
    private lateinit var mEdtRePassword: EditText
    private lateinit var mBtnChangePassword: Button

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        val factory = ChangePasswordDialogFactory(PlayerRepository(PlayerApi()))
        mViewModel =
            ViewModelProviders.of(this, factory).get(ChangePasswordDialogViewModel::class.java)

        val view = activity!!.layoutInflater.inflate(R.layout.dialog_change_password, null)

        mPlayer = arguments!!.getParcelable(PLAYER)!!

        init(view)

        return AlertDialog.Builder(activity!!)
            .dialog(view, R.drawable.ic_change, R.string.change_password)
    }

    private fun init(view: View) {
        mEdtUsername = view.findViewById(R.id.mEdtUsername) as EditText
        mEdtOldPassword = view.findViewById(R.id.mEdtOldPassword) as EditText
        mEdtNewPassword = view.findViewById(R.id.mEdtNewPassword) as EditText
        mEdtRePassword = view.findViewById(R.id.mEdtRePassword) as EditText
        mBtnChangePassword = view.findViewById(R.id.mBtnSave) as Button

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

        mViewModel.updatePassword(mPlayer.playerId!!, oldPassword, newPassword)
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
