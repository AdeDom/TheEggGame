package com.adedom.theegggame.ui.multi.multi

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import com.adedom.library.extension.dialogFragment
import com.adedom.library.extension.toast
import com.adedom.theegggame.R
import com.adedom.theegggame.util.BaseDialogFragment
import com.adedom.theegggame.util.MapActivity
import com.adedom.utility.TEAM_A
import com.adedom.utility.TEAM_B

class EndGameDialog : BaseDialogFragment<MultiActivityViewModel>({ R.layout.dialog_end_game }) {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MultiActivityViewModel::class.java)

        init(bView)

        val teamA = arguments!!.getString(TEAM_A)
        val teamB = arguments!!.getString(TEAM_B)
        MapActivity.sContext.toast("End Game : $teamA , $teamB")

        return AlertDialog.Builder(activity!!)
            .dialogFragment(bView, R.drawable.ic_timer_out_black, R.string.time_out)
    }

    private fun init(view: View) {
    }

}
