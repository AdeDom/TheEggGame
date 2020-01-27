package com.adedom.theegggame.ui.multi.multi

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.adedom.library.extension.dialogFragment
import com.adedom.library.util.BaseDialogFragment
import com.adedom.theegggame.R
import com.adedom.theegggame.ui.multi.roominfo.RoomInfoActivityViewModel
import com.adedom.theegggame.util.*

class EndGameDialog : BaseDialogFragment<RoomInfoActivityViewModel>({ R.layout.dialog_end_game }) {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        init()

        return AlertDialog.Builder(activity!!)
            .dialogFragment(v, R.drawable.the_egg_game, R.string.end_game)
    }

    private fun init() {
        val team = arguments!!.getString(TEAM)!!
        val teamA = arguments!!.getString(TEAM_A)!!
        val teamB = arguments!!.getString(TEAM_B)!!

        val tvTeamA = v.findViewById(R.id.tvTeamA) as TextView
        val tvTeamB = v.findViewById(R.id.tvTeamB) as TextView
        val tvScoreA = v.findViewById(R.id.tvScoreA) as TextView
        val tvScoreB = v.findViewById(R.id.tvScoreB) as TextView
        val btEndGame = v.findViewById(R.id.btEndGame) as Button

        tvScoreA.text = teamA
        tvScoreB.text = teamB

        val teamWin: String
        when {
            teamA > teamB -> {
                tvTeamA.text = getString(R.string.win)
                tvTeamB.text = getString(R.string.lose)
                teamWin = TEAM_A
            }
            teamA < teamB -> {
                tvTeamA.text = getString(R.string.lose)
                tvTeamB.text = getString(R.string.win)
                teamWin = TEAM_B
            }
            else -> {
                tvTeamA.text = getString(R.string.always)
                tvTeamB.text = getString(R.string.always)
                teamWin = TEAM_ALWAYS
            }
        }

        if (team == teamWin) {
            btEndGame.setText(R.string.bonus)
            btEndGame.setOnClickListener {
                dialog!!.dismiss()
                BonusGameDialog().show(activity!!.supportFragmentManager, null)
            }
        } else btEndGame.setOnClickListener { GameActivity.sActivity.finish() }

    }

}
