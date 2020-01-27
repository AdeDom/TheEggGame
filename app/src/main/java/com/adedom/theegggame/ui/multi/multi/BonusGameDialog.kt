package com.adedom.theegggame.ui.multi.multi

import android.app.Dialog
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import com.adedom.library.extension.dialogFragment
import com.adedom.library.util.BaseDialogFragment
import com.adedom.theegggame.R
import com.adedom.theegggame.ui.multi.roominfo.RoomInfoActivityViewModel

class BonusGameDialog :
    BaseDialogFragment<RoomInfoActivityViewModel>({ R.layout.dialog_bonus_game }) {

    private var lastDir = 0F

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(RoomInfoActivityViewModel::class.java)

        init()

        return AlertDialog.Builder(activity!!)
            .dialogFragment(v, R.drawable.ic_egg_bonus, R.string.bonus)
    }

    private fun init() {
        val tvBonus = v.findViewById(R.id.tvBonus) as TextView
        val ivWheel = v.findViewById(R.id.ivWheel) as ImageView
        val btBonus = v.findViewById(R.id.btBonus) as Button

        btBonus.setOnClickListener {
            val newDir = ((0..3600).random() + 720).toFloat()
            val pivotX = ivWheel.width / 2
            val pivotY = ivWheel.height / 2

            val rotate = RotateAnimation(
                lastDir, newDir,
                pivotX.toFloat(), pivotY.toFloat()
            )
            rotate.duration = 2500
            rotate.fillAfter = true
            rotate.interpolator = DecelerateInterpolator()
            lastDir = newDir
            ivWheel.startAnimation(rotate)
        }

    }

}
