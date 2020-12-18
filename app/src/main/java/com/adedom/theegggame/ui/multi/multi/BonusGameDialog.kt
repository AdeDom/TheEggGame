package com.adedom.theegggame.ui.multi.multi

import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.adedom.library.extension.onAnimationEnd
import com.adedom.library.extension.toast
import com.adedom.theegggame.R
import com.adedom.theegggame.util.GameActivity
import com.adedom.theegggame.util.GameActivity.Companion.sContext

//test branch
class BonusGameDialog : DialogFragment() {

    private var onClick = 0
    private var bonus = 0
    private var lastDir = 0F

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvBonus = view.findViewById(R.id.tvBonus) as TextView
        val ivWheel = view.findViewById(R.id.ivWheel) as ImageView
        val btBonus = view.findViewById(R.id.btBonus) as Button

        btBonus.setOnClickListener {
            val newDir = (0..3600).random().plus(720).toFloat()
            val rotate = RotateAnimation(
                lastDir, newDir,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f
            )
            rotate.duration = 2000
            rotate.fillAfter = true
            rotate.interpolator = DecelerateInterpolator()
            rotate.onAnimationEnd {
                bonus += calBonus(360 - newDir % 360)
                tvBonus.text = bonus.toString()

                onClick++
                if (onClick >= 3) {
                    //TODO insert bonus

                    sContext.toast(sContext.resources.getString(R.string.experience_point, bonus))
                    GameActivity.sActivity.finish()
                }
            }
            ivWheel.startAnimation(rotate)
            lastDir = newDir
        }
    }

    private fun calBonus(degree: Float): Int {
        return when {
            degree >= 1 && degree < 90 -> 0
            degree >= 90 && degree < 180 -> 300
            degree >= 180 && degree < 270 -> 400
            degree >= 270 && degree < 360 -> 500
            else -> 0
        }
    }

}
