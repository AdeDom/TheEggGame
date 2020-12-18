package com.adedom.android.presentation.bonusteggame

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.adedom.android.R
import com.adedom.android.base.BaseFragment
import com.adedom.android.util.toast
import com.adedom.teg.presentation.bonusteggame.BonusTegGameViewModel
import kotlinx.android.synthetic.main.fragment_bonus_teg_game.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class BonusTegGameFragment : BaseFragment(R.layout.fragment_bonus_teg_game) {

    private var onClick = 0
    private var bonus = 0
    private var lastDir = 0F
    private val viewModel by viewModel<BonusTegGameViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        observeViewModel()
        viewEvent()
    }

    private fun observeViewModel() {
        viewModel.state.observe { state ->
            animationViewLoading.isVisible = state.isLoading
        }

        viewModel.error.observeError()
    }

    private fun viewEvent() {
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
            rotate.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(p0: Animation?) {
                }

                override fun onAnimationEnd(p0: Animation?) {
                    bonus += calBonus(360 - newDir % 360)
                    tvBonus.text = bonus.toString()

                    onClick++
                    if (onClick >= 3) {
                        //TODO insert bonus

                        val text = getString(R.string.experience_point, bonus)
                        context?.toast(text, Toast.LENGTH_LONG)
                        findNavController().popBackStack()
                    }
                }

                override fun onAnimationRepeat(p0: Animation?) {
                }
            })
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
