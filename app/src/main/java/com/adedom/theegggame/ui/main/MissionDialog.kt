package com.adedom.theegggame.ui.main

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.library.extension.dialogFragment
import com.adedom.library.extension.readPrefFile
import com.adedom.library.extension.toast
import com.adedom.library.extension.writePrefFile
import com.adedom.library.util.BaseDialogFragment
import com.adedom.theegggame.R
import com.adedom.theegggame.util.GameActivity
import com.adedom.theegggame.util.KEY_COMPLETED
import com.adedom.theegggame.util.KEY_MISSION_DELIVERY
import com.adedom.theegggame.util.KEY_MISSION_SUCCESSFUL

class MissionDialog : BaseDialogFragment<MainActivityViewModel>({ R.layout.dialog_mission }) {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        init(v)

        return AlertDialog.Builder(activity!!)
            .dialogFragment(v, R.drawable.ic_mission, R.string.mission)
    }

    private fun init(v: View) {
        val tvDeliveryLogin = v.findViewById(R.id.tvDeliveryLogin) as TextView
        val ivCorrect = v.findViewById(R.id.ivCorrect) as ImageView

        if (GameActivity.sContext.readPrefFile(KEY_MISSION_DELIVERY) == KEY_MISSION_SUCCESSFUL) {
            ivCorrect.visibility = View.VISIBLE
        } else {
            tvDeliveryLogin.setOnClickListener {
                GameActivity.sContext.writePrefFile(KEY_MISSION_DELIVERY, KEY_MISSION_SUCCESSFUL)
                ivCorrect.visibility = View.VISIBLE

                missionComplete(R.string.delivery_login)
            }
        }
    }

    private fun missionComplete(messages: Int) {
        viewModel.missionComplete(MainActivity.sPlayer.playerId).observe(this, Observer {
            if (it.result == KEY_COMPLETED) GameActivity.sContext.toast(messages)
        })
    }
}
