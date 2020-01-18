package com.adedom.theegggame.ui.main

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.adedom.library.extension.dialogFragment
import com.adedom.library.util.BaseDialogFragment
import com.adedom.theegggame.R

class MissionDialog : BaseDialogFragment<MainActivityViewModel>({ R.layout.dialog_mission }) {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        return AlertDialog.Builder(activity!!)
            .dialogFragment(v, R.drawable.ic_mission, R.string.mission)
    }
}
