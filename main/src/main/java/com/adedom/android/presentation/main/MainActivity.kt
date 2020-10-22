package com.adedom.android.presentation.main

import android.os.Bundle
import com.adedom.android.R
import com.adedom.android.base.BaseLocationActivity
import com.adedom.teg.presentation.main.MainViewModel
import com.adedom.teg.util.TegConstant
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseLocationActivity() {

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.callLogActiveOn()

        viewModel.error.observeError()
    }

    override fun onResume() {
        super.onResume()
        viewModel.callPlayerState(TegConstant.STATE_ONLINE)
    }

    override fun onPause() {
        super.onPause()
        viewModel.callPlayerState(TegConstant.STATE_OFFLINE)
        viewModel.callLogActiveOff()
    }

}
