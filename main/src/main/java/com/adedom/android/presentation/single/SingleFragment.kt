package com.adedom.android.presentation.single

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.navigation.fragment.findNavController
import com.adedom.android.R
import com.adedom.android.base.BaseFragment
import com.adedom.android.util.setVisibility
import com.adedom.teg.presentation.single.SingleViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import kotlinx.android.synthetic.main.fragment_single.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SingleFragment : BaseFragment(R.layout.fragment_single), OnMapReadyCallback {

    private val viewModel by viewModel<SingleViewModel>()
    private var mGoogleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        viewModel.state.observe { state ->
            progressBar.setVisibility(state.loading)
        }

        viewModel.error.observeError()

        btItem.setOnClickListener {
            viewModel.callItemCollection()
        }

        floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_singleFragment_to_backpackFragment)
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mGoogleMap = googleMap
    }

    override fun onResume() {
        mapView.onResume()
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

}
