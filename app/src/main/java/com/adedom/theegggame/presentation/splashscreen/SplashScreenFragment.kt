package com.adedom.theegggame.presentation.splashscreen

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.adedom.teg.presentation.splashscreen.SplashScreenViewModel
import com.adedom.theegggame.R
import com.adedom.theegggame.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashScreenFragment : BaseFragment(R.layout.fragment_splash_screen) {

    private val viewModel by viewModel<SplashScreenViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.attachFirstTime.observe {
            if (viewModel.initialize()) {
                findNavController().navigate(R.id.action_splashScreenFragment_to_mainFragment)
            } else {
                findNavController().navigate(R.id.action_splashScreenFragment_to_signInFragment)
            }
        }

        viewModel.error.observeError()
    }

}
