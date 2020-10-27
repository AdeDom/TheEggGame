package com.adedom.android.presentation.main

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.asLiveData
import com.adedom.android.R
import com.adedom.android.base.BaseLocationActivity
import com.adedom.android.util.locationFlow
import com.adedom.android.util.toast
import com.adedom.teg.presentation.main.MainViewModel
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.conflate
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class MainActivity : BaseLocationActivity() {

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LocationServices.getFusedLocationProviderClient(this)
            .locationFlow()
            .conflate()
            .catch { e ->
                toast(e.message, Toast.LENGTH_LONG)
            }
            .asLiveData()
            .observe {
                viewModel.callChangeLatLng(it.latitude, it.longitude)
            }

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
