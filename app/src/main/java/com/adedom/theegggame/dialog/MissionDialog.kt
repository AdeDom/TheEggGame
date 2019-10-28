package com.adedom.theegggame.dialog

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import com.adedom.theegggame.R

class MissionDialog : DialogFragment() { // 15/7/62

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity!!.layoutInflater.inflate(R.layout.dialog_mission, null)

        val builder = AlertDialog.Builder(activity!!)
            .setView(view)
            .setIcon(R.drawable.ic_mission)
            .setTitle("Mission")

        return builder.create()
    }
}
