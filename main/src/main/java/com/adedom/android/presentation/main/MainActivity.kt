package com.adedom.android.presentation.main

import android.os.Bundle
import com.adedom.android.R
import com.adedom.android.base.BaseLocationActivity
import com.adedom.teg.presentation.main.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
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
        viewModel.callPlayerStateOnline()
    }

    override fun onPause() {
        super.onPause()
        viewModel.callPlayerStateOffline()
        viewModel.callLogActiveOff()
    }

}
