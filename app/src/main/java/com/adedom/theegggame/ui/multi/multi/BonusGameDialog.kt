package com.adedom.theegggame.ui.multi.multi

import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import com.adedom.library.util.BaseDialogFragment
import com.adedom.theegggame.R
import com.adedom.theegggame.ui.multi.roominfo.RoomInfoActivityViewModel

class BonusGameDialog : BaseDialogFragment<RoomInfoActivityViewModel>(
    { R.layout.dialog_bonus_game },
    { R.drawable.ic_egg_bonus },
    { R.string.bonus }
) {

    private var lastDir = 0F

    override fun initDialog(view: View) {
        super.initDialog(view)
        viewModel = ViewModelProviders.of(this).get(RoomInfoActivityViewModel::class.java)

        val tvBonus = view.findViewById(R.id.tvBonus) as TextView
        val ivWheel = view.findViewById(R.id.ivWheel) as ImageView
        val btBonus = view.findViewById(R.id.btBonus) as Button

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
