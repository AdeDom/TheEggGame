package com.adedom.android.presentation.single

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.adedom.android.R
import com.adedom.android.base.BaseFragment
import com.adedom.android.util.*
import com.adedom.teg.presentation.single.SingleViewEvent
import com.adedom.teg.presentation.single.SingleViewModel
import com.adedom.teg.presentation.single.SingleViewState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_single.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@FlowPreview
@ExperimentalCoroutinesApi
class SingleFragment : BaseFragment(R.layout.fragment_single) {

    private val viewModel by viewModel<SingleViewModel>()
    private var mMarkerMyLocation: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val locationProviderClient = LocationServices
            .getFusedLocationProviderClient(requireActivity())

        mapView.onCreate(savedInstanceState)

        launch {
            val googleMap = mapView.getGoogleMap()

            googleMap.isMyLocationEnabled = true
            googleMap.setMinZoomPreference(12F)
            googleMap.setMaxZoomPreference(16F)

            val location = locationProviderClient.awaitLastLocation()
            val latLng = LatLng(location.latitude, location.longitude)
            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 14F)
            googleMap.animateCamera(cameraUpdate)

            locationProviderClient
                .locationFlow()
                .onEach { viewModel.setStateLatLng(it.latitude, it.longitude) }
                .catch { context.toast(it.message, Toast.LENGTH_LONG) }
                .launchIn(this)
        }

        viewModel.state.observe { state ->
            progressBar.setVisibility(state.loading)

            // marker
            setMarkerMyLocation(state)
        }

        viewModel.error.observeError()

        viewModel.getDbPlayerInfoLiveData.observe(viewLifecycleOwner, { playerInfo ->
            if (playerInfo == null) return@observe

            context?.setImageCircle(playerInfo.image, onResourceReady = { bitmap ->
                viewModel.setStateBitmap(bitmap)
            }, onLoadCleared = { bitmap ->
                viewModel.setStateBitmap(bitmap)
            })
        })

        viewModel.singleViewEvent.observe { event ->
            when (event) {
                is SingleViewEvent.BackpackFragment -> {
                    findNavController().navigate(R.id.action_singleFragment_to_backpackFragment)
                }
                else -> {
                }
            }
        }

        viewEvent().observe { viewModel.process(it) }
    }

    private fun viewEvent(): Flow<SingleViewEvent> {
        return merge(
            btItem.clicks().map { SingleViewEvent.CallItemCollection },
            floatingActionButton.clicks().map { SingleViewEvent.BackpackFragment },
        )
    }

    private fun setMarkerMyLocation(state: SingleViewState) {
        launch {
            val googleMap = mapView.getGoogleMap()

            val latLng = LatLng(state.latLng.latitude, state.latLng.longitude)

            val markerOptions = MarkerOptions().apply {
                position(latLng)
                title(state.name)
                snippet(getString(R.string.level, state.level))
            }

            state.bitmap?.let {
                mMarkerMyLocation?.remove()

                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(state.bitmap))
                mMarkerMyLocation = googleMap.addMarker(markerOptions)
            }
        }
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
