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
import com.adedom.theegggame.util.*

class MissionDialog : BaseDialogFragment<MainActivityViewModel>({ R.layout.dialog_mission }) {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        init(v)

        return AlertDialog.Builder(activity!!)
            .dialogFragment(v, R.drawable.ic_mission, R.string.mission)
    }

    private fun init(v: View) {
        val tvMissionDelivery = v.findViewById(R.id.tvMissionDelivery) as TextView
        val tvMissionSingle = v.findViewById(R.id.tvMissionSingle) as TextView
        val tvMissionMulti = v.findViewById(R.id.tvMissionMulti) as TextView
        val ivCorrectDelivery = v.findViewById(R.id.ivCorrectDelivery) as ImageView
        val ivCorrectSingle = v.findViewById(R.id.ivCorrectSingle) as ImageView
        val ivCorrectMulti = v.findViewById(R.id.ivCorrectMulti) as ImageView

        if (GameActivity.sContext.readPrefFile(KEY_MISSION_DELIVERY) == KEY_MISSION_SUCCESSFUL) {
            ivCorrectDelivery.visibility = View.VISIBLE
        } else {
            tvMissionDelivery.setOnClickListener {
                GameActivity.sContext.writePrefFile(KEY_MISSION_DELIVERY, KEY_MISSION_SUCCESSFUL)
                ivCorrectDelivery.visibility = View.VISIBLE
                missionComplete(R.string.delivery_login)
                tvMissionDelivery.isClickable = false
            }
        }

        if (GameActivity.sContext.readPrefFile(KEY_MISSION_SINGLE) == KEY_MISSION_SUCCESSFUL)
            ivCorrectSingle.visibility = View.VISIBLE
        if (GameActivity.sContext.readPrefFile(KEY_MISSION_MULTI) == KEY_MISSION_SUCCESSFUL)
            ivCorrectMulti.visibility = View.VISIBLE

        if (GameActivity.sContext.readPrefFile(KEY_MISSION_SINGLE_GAME) == KEY_MISSION_SUCCESSFUL) {
            tvMissionSingle.setOnClickListener {
                GameActivity.sContext.writePrefFile(KEY_MISSION_SINGLE, KEY_MISSION_SUCCESSFUL)
                GameActivity.sContext.writePrefFile(
                    KEY_MISSION_SINGLE_GAME,
                    KEY_MISSION_SINGLE_GAME
                )
                ivCorrectSingle.visibility = View.VISIBLE
                missionComplete(R.string.mission_single)
                tvMissionSingle.isClickable = false
            }
        }

        if (GameActivity.sContext.readPrefFile(KEY_MISSION_MULTI_GAME) == KEY_MISSION_SUCCESSFUL) {
            tvMissionMulti.setOnClickListener {
                GameActivity.sContext.writePrefFile(KEY_MISSION_MULTI, KEY_MISSION_SUCCESSFUL)
                GameActivity.sContext.writePrefFile(
                    KEY_MISSION_MULTI_GAME,
                    KEY_MISSION_MULTI_GAME
                )
                ivCorrectMulti.visibility = View.VISIBLE
                missionComplete(R.string.multi_player)
                tvMissionMulti.isClickable = false
            }
        }

    }

    private fun missionComplete(messages: Int) {
        viewModel.missionComplete(MainActivity.sPlayer.playerId).observe(this, Observer {
            if (it.result == KEY_COMPLETED) GameActivity.sContext.toast(messages)
        })
    }
}
