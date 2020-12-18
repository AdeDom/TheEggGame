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

    private val viewModel by viewModel<BonusTegGameViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        observeViewModel()
        viewEvent()
    }

    private fun observeViewModel() {
        viewModel.state.observe { state ->
            animationViewLoading.isVisible = state.isLoading

            tvBonus.text = state.bonusPoint.toString()

            if (state.isRotateBonusCompleted) {
                val text = getString(R.string.experience_point, viewModel.state.value?.bonusPoint)
                context?.toast(text, Toast.LENGTH_LONG)
                viewModel.callBonusTegGame()
            }
        }

        viewModel.bonusTegGameEvent.observe { response ->
            if (response.success) {
                findNavController().popBackStack()
            }
        }

        viewModel.error.observeError()
    }

    private fun viewEvent() {
        btBonus.setOnClickListener {
            viewModel.setStateNewDir()
            RotateAnimation(
                viewModel.state.value?.lastDir ?: 0F,
                viewModel.state.value?.newDir ?: 0F,
                RotateAnimation.RELATIVE_TO_SELF, 0.5F,
                RotateAnimation.RELATIVE_TO_SELF, 0.5F,
            ).apply {
                duration = 2000
                fillAfter = true
                interpolator = DecelerateInterpolator()
                setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(p0: Animation?) {
                    }

                    override fun onAnimationEnd(p0: Animation?) {
                        viewModel.setStateAnimationEnd()
                    }

                    override fun onAnimationRepeat(p0: Animation?) {
                    }
                })
                ivWheel.startAnimation(this)
            }
        }
    }

}
